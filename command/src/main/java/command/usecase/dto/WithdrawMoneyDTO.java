package command.usecase.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WithdrawMoneyDTO {
    private String accountId;
    private String holderId;
    private Long amount;
    private static final String ACTION = "withdrawal";
}
