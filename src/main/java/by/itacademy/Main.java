package by.itacademy;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext ctx =
                new ClassPathXmlApplicationContext("context.xml");
    }
}
