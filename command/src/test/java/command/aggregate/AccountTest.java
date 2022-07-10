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
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

class AccountTest {
    
    private final FixtureConfiguration<Account> testFixture = new AggregateTestFixture<>(Account.class);
    
    private static final String holderId = UUID.randomUUID().toString();
    private static final String accountId = UUID.randomUUID().toString();
    
    @Test
    @DisplayName("CreateAccountCommand 를 execute 하면 " +
                 "성공적으로 실행되고" +
                 "AccountCreated event 가 발행(publish)된다. ")
    void test01() {
        testFixture.givenNoPriorActivity()
                   .when(new CreateAccountCommand(accountId, holderId))
                   .expectSuccessfulHandlerExecution()
                   .expectEvents(new AccountCreated(accountId, holderId));
    }
    
    @ParameterizedTest
    @DisplayName("AccountCreated 이벤트가 있었을 때, " +
                 "0 초과 값의 amount 로 DepositMoneyCommand 를 execute 하면 " +
                 "성공적으로 실행되고 " +
                 "MoneyDeposited event 가 발행(publish)된다. ")
    @ValueSource(longs = {1L, 2L, Long.MAX_VALUE})
    void test02(Long amount) {
        testFixture.given(new AccountCreated(accountId, holderId))
                   .when(new DepositMoneyCommand(accountId, holderId, amount))
                   .expectSuccessfulHandlerExecution()
                   .expectEvents(new MoneyDeposited(accountId, holderId, amount));
    }
    
    @ParameterizedTest
    @DisplayName("AccountCreated 이벤트가 있었을 때, " +
                 "0 이하의 amount로 DepositMoneyCommand 를 execute 하면 " +
                 "CannotDepositLessThanZeroException 이 발생한다.")
    @ValueSource(longs = {0L, -1L, -2L, Long.MIN_VALUE})
    void test03(Long amount) {
        testFixture.given(new AccountCreated(accountId, holderId))
                   .when(new DepositMoneyCommand(accountId, holderId, amount))
                   .expectException(CannotDepositLessThanZeroException.class);
    }
    
    @ParameterizedTest
    @DisplayName("AccountCreated 이벤트가 있었고," +
                 "MoneyDeposited 이벤트가 특정 amount 로 있었을때, " +
                 "0 초과 값의 amount 로 WithdrawMoneyCommand 를 execute 하면 " +
                 "성공적으로 실행되고 " +
                 "MoneyDeposited event 가 발행(publish)된다. ")
    @ValueSource(longs = {1L, 2L, Long.MAX_VALUE})
    void test04(Long amount) {
        testFixture.given(new AccountCreated(accountId, holderId))
                .andGiven(new MoneyDeposited(accountId, holderId, amount))
                   .when(new WithdrawMoneyCommand(accountId, holderId, amount))
                   .expectSuccessfulHandlerExecution()
                   .expectEvents(new MoneyWithdrawn(accountId, holderId, amount));
        
        testFixture.given(new AccountCreated(accountId, holderId))
                   .andGiven(new MoneyDeposited(accountId, holderId, amount))
                   .andGiven(new MoneyDeposited(accountId, holderId, amount)) // 두번 예치해도 똑같이 가능
                   .when(new WithdrawMoneyCommand(accountId, holderId, amount))
                   .expectSuccessfulHandlerExecution()
                   .expectEvents(new MoneyWithdrawn(accountId, holderId, amount));
    }
    
    @ParameterizedTest
    @DisplayName("AccountCreated 이벤트가 있었고, " +
                 "MoneyDeposited 이벤트가 특정 amount 로 있었을때, " +
                 "0 이하의 amount 로 WithdrawMoneyCommand 를 execute 하면 " +
                 "CannotWithdrawLessThanZeroException 이 발생한다.")
    @ValueSource(longs = {0L, -1L, -2L, Long.MIN_VALUE})
    void test05(Long amount) {
        testFixture.given(new AccountCreated(accountId, holderId))
                   .andGiven(new MoneyDeposited(accountId, holderId, amount))
                   .when(new WithdrawMoneyCommand(accountId, holderId, amount))
                   .expectException(CannotWithdrawLessThanZeroException.class);
    }
    
    @ParameterizedTest
    @DisplayName("AccountCreated 이벤트가 있었고, " +
                 "MoneyDeposited 이벤트가 특정 amount 로 있었을때, " +
                 "계좌 잔고보다 많은 금액으로 WithdrawMoneyCommand 를 execute 하면 " +
                 "NotEnoughBalanceException 이 발생한다.")
    @ValueSource(longs = {1L, 2L, Long.MAX_VALUE})
    void test06(Long amount) {
        testFixture.given(new AccountCreated(accountId, holderId))
                   .andGiven(new MoneyDeposited(accountId, holderId, amount))
                   .when(new WithdrawMoneyCommand(accountId, holderId, amount+amount)) // Max value 를 넘어가도 유효하다
                   .expectException(NotEnoughBalanceException.class);
    }
}