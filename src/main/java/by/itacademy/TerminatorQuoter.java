package by.itacademy;

public class TerminatorQuoter implements Quoter {

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
        System.out.println(message);
    }

    public String getMessage() {
        return message;
    }
}
