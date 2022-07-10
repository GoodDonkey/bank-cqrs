package command.aggregate;

import com.bankcqrs.events.HolderCreated;
import command.commands.CreateHolderCommand;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Slf4j
@Aggregate
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Holder {
    @AggregateIdentifier
    private String holderId;
    private String holderName;
    private String phoneNumber;
    private String address;
    
    @CommandHandler
    public Holder(CreateHolderCommand command) {
        log.debug("handling {}", command);
        apply(new HolderCreated(command.getHolderId(), command.getHolderName(), command.getPhoneNumber(), command.getAddress()));
    }
    
    @EventSourcingHandler
    protected void on(HolderCreated event) {
        log.debug("applying {}", event);
        this.holderId = event.getHolderId();
        this.holderName = event.getHolderName();
        this.phoneNumber = event.getPhoneNumber();
        this.address = event.getAddress();
    }
}
