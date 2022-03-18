package by.itacademy.screensaver;

import lombok.SneakyThrows;
import org.springframework.context.annotation.*;

import java.awt.*;
import java.util.Random;

@Configuration
@ComponentScan(basePackages = "by.itacademy.screensaver")
public class Config {

    @Bean
    @Scope("prototype")
//    @Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
//    - при каждом обращении к переменной color будет создаваться новый bean
//            System.out.println(color) - новый Bean;
//            System.out.println(color) - ещё новый Bean;

    public Color color() {
        Random random = new Random();
        return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    /**
     * Создать объект из абстрактного класса можно только если прописать абстракный метод
     * Config.java - естественное место, где можно получить context.getBean(Color)
     */

    //@Scope("prototype") Создастся при каждом вызове бина ColorFrame новый экземпляр окна
    //По заданию окно должно быть одно поэтому @Scope должен быть ("singltone")
    @Bean
    @Scope("singleton")
    public ColorFrame colorFrame() {
        return new ColorFrame() {
            @Override
            protected Color getColor() {
                return color();
            }
        };
    }

    /**
     * Задача сделать перемесчающееся окно(Singleton) изменяющее цвет(Prototype)
     * Как обновить Prototype в Singleton
     */

    @SneakyThrows
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        while (true) {
            /** Prototype внутри Singleton тоже становится Singleton
             *
             *  ColorFrame - Singleton и он кладётся в context единожды,
             *  а вместе с ним кладётся и зависимый bean Color, хоть он и является Prototype
             *  в context Color попадает тоже единожды вместе с Frame`ом.
             */

            context.getBean(ColorFrame.class).showOnRandomPlace();
            Thread.sleep(500);
        }
    }

}
