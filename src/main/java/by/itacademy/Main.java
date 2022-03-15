package by.itacademy;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        /**
         * Реализация этого контекста анализируется и сканируется
         * XmlBeanDefinitionReader
         */
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");

        /**
         * Bean можно вытащить по классу и это не правильно
         */
        context.getBean(TerminatorQuoter.class).sayQuote();
    }
}
