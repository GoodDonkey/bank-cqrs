package command.aggregate;

import com.bankcqrs.events.AccountCreated;
import com.bankcqrs.events.MoneyDeposited;
import com.bankcqrs.events.MoneyWithdrawn;
import command.commands.CreateAccountCommand;
import command.commands.DepositMoneyCommand;
import command.commands.WithdrawMoneyCommand;
import command.exception.CannotDepositLessThanZeroException;
import command.exception.CannotWithdrawLessThanZeroException;
import command.exception.NotEnoughBalanceException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Slf4j
@Aggregate(
        snapshotTriggerDefinition = "accountSnapshotTriggerDefinition",
        repository = "accountRepository",
        snapshotFilter = "accountSnapshotFilter"
)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Account {
    @AggregateIdentifier
    private String accountId;
    private String holderId;
    private Long balance;
    
    @CommandHandler
    public Account(CreateAccountCommand command) {
        log.debug("handling {}", command);
        apply(new AccountCreated(command.getAccountId(), command.getHolderId()));
    }
    
    @EventSourcingHandler
    protected void on(AccountCreated event) {
        log.debug("applying {}", event);
        this.accountId = event.getAccountId();
        this.holderId = event.getHolderId();
        this.balance = 0L;
    }
    
    @CommandHandler
    protected void handle(DepositMoneyCommand command) {
        log.debug("handling {}", command);
        if (command.getAmount() <= 0) throw new CannotDepositLessThanZeroException();
        apply(new MoneyDeposited(command.getAccountId(), command.getHolderId(), command.getAmount()));
    }
    
    @EventSourcingHandler
    protected void on(MoneyDeposited event) {
        log.debug("applying {}", event);
        this.balance += event.getAmount();
        log.debug("balance after deposit: {}", balance);
    }
    
    @CommandHandler
    protected void handle(WithdrawMoneyCommand command) {
        log.debug("handling {}", command);
        if (this.balance - command.getAmount() < 0) throw new NotEnoughBalanceException();
        if (command.getAmount() <= 0) throw new CannotWithdrawLessThanZeroException();
        apply(new MoneyWithdrawn(command.getAccountId(), command.getHolderId(), command.getAmount()));
    }
    
    @EventSourcingHandler
    protected void on(MoneyWithdrawn event) {
        log.debug("applying {}", event);
        this.balance -= event.getAmount();
        log.debug("balance after withdraw: {}", balance);
    }
}
