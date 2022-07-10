package command.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@AllArgsConstructor
@Getter
@ToString
public class CreateHolderCommand {
    @TargetAggregateIdentifier
    private String holderId;
    private String holderName;
    private String phoneNumber;
    private String address;
}
