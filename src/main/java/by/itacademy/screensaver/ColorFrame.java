package by.itacademy.screensaver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public abstract class ColorFrame extends JFrame {

    @Autowired
//  Класс Color не наш и невозможно поставить над классом аннотацию @Component
//  Поэтому создаём Bean Definition в Java или XML конфигурации
    private Color color;

//  Самым неправильным решением проблемы Как обновить Prototype в Singleton
//  было бы добавить в бизнесс-модель context - ХОРОШИЙ ПРИМЕР НАРУШЕНИЯ принципа low-coupling

//  Если context в классе бизнесс-модели нарушает принцип low-coupling
//  вынесем context за рамки класса, а сам класс сделаем абстрактным

    public ColorFrame() {
        setSize(200, 200);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     *  По методу showOnRandomPlace меняется расположение и цвет окна
     */

    public void showOnRandomPlace(){
        Random random = new Random();
        setLocation(random.nextInt(1000), random.nextInt(600));
        getContentPane().setBackground(getColor());
        repaint();

    }

    protected abstract Color getColor();
}
