package command.usecase.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class DepositMoneyDTO {
    
    @NotBlank
    private String accountId;
    
    @NotBlank
    private String holderId;
    
    @NotNull
    @Positive(message = "0 보다 큰 값이어야 합니다.")
    private Long amount;
    
    @NotNull
    @Pattern(regexp = "deposit")
    private String action;
}
