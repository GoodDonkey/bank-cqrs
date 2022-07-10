package command.aggregate;

import lombok.AllArgsConstructor;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@AllArgsConstructor
public class Holder {
    @AggregateIdentifier
    private String holderId;
    private String accountId;
    private String phoneNumber;
    private String address;
}
