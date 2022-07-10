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
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {
    @AggregateIdentifier
    private String accountId;
    private String holderId;
    private Long balance;
    
    @CommandHandler
    public Account(CreateAccountCommand command) {
        apply(new AccountCreated(command.getHolderId(), command.getAccountId()));
    }
    
    @EventSourcingHandler
    protected void createAccount(AccountCreated event) {
        this.accountId = event.getAccountId();
        this.holderId = event.getHolderId();
        this.balance = 0L;
    }
    
    @CommandHandler
    protected void depositMoney(DepositMoneyCommand command) {
        if (command.getAmount() <= 0) throw new CannotDepositLessThanZeroException();
        apply(new MoneyDeposited(command.getHolderId(), command.getAccountId(), command.getAmount()));
    }
    
    @EventSourcingHandler
    protected void depositMoney(MoneyDeposited event) {
        this.balance += event.getAmount();
    }
    
    @CommandHandler
    protected void depositMoney(WithdrawMoneyCommand command) {
        if (this.balance - command.getAmount() < 0) throw new NotEnoughBalanceException();
        if (command.getAmount() <= 0) throw new CannotWithdrawLessThanZeroException();
        apply(new MoneyWithdrawn(command.getHolderId(), command.getAccountId(), command.getAmount()));
    }
    
    @EventSourcingHandler
    protected void withdrawMoney(MoneyWithdrawn event) {
        this.balance -= event.getAmount();
    }
}
