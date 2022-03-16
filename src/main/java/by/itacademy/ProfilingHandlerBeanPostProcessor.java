package by.itacademy;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ProfilingHandlerBeanPostProcessor implements BeanPostProcessor {

    public ProfilingHandlerBeanPostProcessor() throws Exception {

        /**
         * Получаем instance сервера в котором можно регистрировать bean
         */
        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
        platformMBeanServer.registerMBean(controller, new ObjectName("profiling", "name", "controller"));
    }

    /**
     * Поскольку у нас есть (Object bean и String beanName) мы можем запомнить их
     * на этапе Before и воспользоваться позже на этапе After
     */

    private Map<String, Class> map = new HashMap<String, Class>();

    private ProfilingController controller = new ProfilingController();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        /**
         * Сделаем проверку есть ли аннотация @Profiling над пришедшим классом
         * Сохраняем его в map
         */
        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(Profiling.class)) {
            map.put(beanName, beanClass);
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        /**
         * Есть ли в map имя пришедшего Bean - beanName;
         * Если этот класс не null - значит он был заполнен на стадии Before
         * Если он есть в map значит над классом стоит аннотация @Profiling
         * Значит вернем объект сгенерированный Dynamic Proxy - Proxy
         */

        Class beanClass = map.get(beanName);
        if (beanClass != null) {
            //Создаёт объект из класса, который он генерирует налету
            /**
             * Принимает 3 вещи:
             *  1. ClassLoader, который загрузит (в Perm или Heap) класс, который сгенерируется налету
             *  2. Список интерфейсов, который должен имплементрировать тот класс, который сгенерируется налету
             *  3. Handler - инкапсулирует логику, которая попадет во все методы и классы которые сгенерируется налету
             */
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object o, Method method, Object[] args) throws Throwable {
                    /**
                     * В классе Profiling controller - создадим (boolean) флаг - выключающий JMX Console;
                     * Java Management Extensions, JMX) — технология Java,
                     * предназначенная для контроля и управления приложениями, системными объектами, устройствами
                     */

                    /**
                     * Описываем логику, которая будет:
                     * - в каждом методе класса, который сгенерируется на лету,
                     * - который имплементирут интерфейсы оригинального класса
                     */


                    if(controller.isEnabled()) {
                        System.out.println("Профилирую...");

                        //Зафиксируем время начала работы метода
                        long before = System.nanoTime();

                        //Вызов оригинального метода, передаем оригинальный bean и аргументы оригинального метода
                        Object retVal = method.invoke(bean, args);

                        //Зафиксируем времени завершения метода
                        long after = System.nanoTime();

                        System.out.print("Время работы метода: ");
                        System.out.println(after - before);
                        System.out.println("Все!");

                        return retVal;
                    } else {
                        return method.invoke(bean, args);
                    }
                }
            });
        }

//        Возвращает Proxy, который ничего не делает если флаг controller.isEnabled()
        return bean;
    }
}
