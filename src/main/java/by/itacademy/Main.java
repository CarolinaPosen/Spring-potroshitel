package by.itacademy;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        /**
         * Реализация этого контекста анализируется и сканируется
         * XmlBeanDefinitionReader
         */
        ConfigurableApplicationContext ctx =
                new ClassPathXmlApplicationContext("context.xml");

        /**
         * Bean можно вытащить по классу и это не правильно
         */

        while (true){
            Thread.sleep(100);

            ctx.getBean(Quoter.class).sayQuote();
        }

        /**
         * Полезные методы в debug:
         * ctx.getBeanDefinitionNames() - имена всех методов в context;
         */

    }
}
