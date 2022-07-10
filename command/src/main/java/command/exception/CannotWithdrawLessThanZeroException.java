package command.exception;

public class CannotWithdrawLessThanZeroException extends IllegalStateException{
    
    private static final String description = "0 이하의 금액은 인출할 수 없습니다.";
    
    public CannotWithdrawLessThanZeroException() {
        super(description);
    }
}
