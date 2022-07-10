package command.exception;

public class CannotDepositLessThanZeroException extends IllegalStateException{
    
    private static final String description = "0 이하의 금액은 예치할 수 없습니다.";
    
    public CannotDepositLessThanZeroException() {
        super(description);
    }
}
