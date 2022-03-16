package by.itacademy;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Profiling
public class TerminatorQuoter implements Quoter {

    /**
     * Каждый раз при создании bean этому полю будет присваиваться значение
     * от 2 до 7
     */
    @InjectRandomInt(min = 2, max = 7)
    private int repeat;

    private String message;

    //(Конструктор - Phase 2)
    public void init(){
        System.out.println("Phase 2");
        System.out.println(repeat);
    }

    //(Конструктор - Phase 1)
    public TerminatorQuoter() {
        System.out.println("Phase 1");
    }

    /**
     Если настраиваем через xml необходим Setter
     Spring через Reflection вызывает Setter
     */
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void sayQuote() {
        for (int i = 0; i < repeat; i++) {
            System.out.println(message);
        }
    }

    public String getMessage() {
        return message;
    }
}
