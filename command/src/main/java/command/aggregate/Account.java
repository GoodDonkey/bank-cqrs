package command.aggregate;

import lombok.AllArgsConstructor;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@AllArgsConstructor
public class Account {
    @AggregateIdentifier
    private String accountId;
    private String holderId;
    private Long balance;
}
