package command.exception;

public class NotEnoughBalanceException extends IllegalStateException {
    
    private static final String description = "잔고가 부족합니다.";
    
    public NotEnoughBalanceException() {
        super(description);
    }
}
