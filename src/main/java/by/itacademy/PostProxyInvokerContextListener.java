package by.itacademy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.Method;

public class PostProxyInvokerContextListener implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * 3-ёх фазовый конструктор на основе ContextRefreshedEvent:
     *
     * ApplicationListener умее слушать context Spring
     * Существует 4 события, которые слушает
     * 1. ContextStartedEvent - context начал своё построение но не еще не закончил
     * 2. ContextStoppedEvent
     * 3. ContextRefreshedEvent - когда заканчивает своё построение
     * 4. ContextClosedEvent
     *
     * Пример:
     * У Service есть метод warmCache, который делает предварительную работу с БД
     * наполняет свою map и после этого говорит, что готов.
     *
     * Где этот метод должен быть вызван?
     *
     * - На этапе работы с конструктором
     *      - ничего не настроено: нет инжектированного DataSource
     *      - Bean нету
     *
     * - На этапе init-method
     *      - @Transaction не существуют на этапе init-method
     *      (логика @Transaction реализована только на следующем этапе
     *      postProcessAfterInitialization, Транзакции метод warmCache
     *      не работают)
     *
     * - 3 фаза конструктора - аннотация @PostProxy
     *      Все методы аннотированные @PostProxy должны автоматически запускаться
     *      после того как Proxy уже сгенирировались

     */

    @Autowired
    private ConfigurableListableBeanFactory factory;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //Вытаскиваем Context
        ApplicationContext context = event.getApplicationContext();
        //Из контекста вытаскиваем имена всех своих Bean`ов
        String[] names = context.getBeanDefinitionNames();
        //Проитерируемся по именам и проверим над какими стоит аннотация @PostProxy

        for (String name : names) {

            /**
             * Есть 2 класса:
             *  1. TerminatorQuoter;
             *  2. Автоматически сгенерированный Proxy7
             *
             *  Методы и сигнатуры у этих классов одинаковые
             */

            //name.getClass() - возвратит Proxy, а нужен класс

            BeanDefinition beanDefinition = factory.getBeanDefinition(name);

            //Вытащим из BeanDefinition назание класса, кторое прописано в context.xml
            String originalClassName = beanDefinition.getBeanClassName();

            //Получаем класс по имени

            try {
                Class<?> originalClass = Class.forName(originalClassName);
                //Вытаскиваем все методы у класса
                Method[] methods = originalClass.getMethods();

                //Итерируемся по методам
                for (Method method : methods) {
                    if (method.isAnnotationPresent(PostProxy.class)) {

                        //Получим bean из context
                        Object bean = context.getBean(name);

                        //Вытащим класс Proxy
                        Method currentMethod = bean.getClass().getMethod(method.getName(), method.getParameterTypes());

                    currentMethod.invoke(bean);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }


    }
}
