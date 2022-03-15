package by.itacademy;

public class TerminatorQuoter implements Quoter {

    /**
     * Каждый раз при создании bean этому полю будет присваиваться значение
     * от 2 до 7
     */
    @InjectRandomInt(min = 2, max = 7)
    private int repeat;

    private String message;

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
