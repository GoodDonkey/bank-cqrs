package command.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@AllArgsConstructor
@Getter
@ToString
public class WithdrawMoneyCommand {
    @TargetAggregateIdentifier
    private String accountId;
    private String holderId;
    private Long amount;
}
