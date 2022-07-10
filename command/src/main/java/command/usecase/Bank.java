package command.usecase;

import command.usecase.dto.CreateAccountDTO;
import command.usecase.dto.CreateHolderDTO;
import command.usecase.dto.DepositMoneyDTO;
import command.usecase.dto.WithdrawMoneyDTO;

import java.util.concurrent.CompletableFuture;

public abstract class Bank {
    
    public interface CreateHolder {
        CompletableFuture<String> createHolder(CreateHolderDTO dto);
    }
    
    public interface CreateAccount {
        CompletableFuture<String> createAccount(CreateAccountDTO dto);
    }
    
    public interface WithdrawMoney {
        CompletableFuture<String> withdraw(WithdrawMoneyDTO dto);
    }
    
    public interface DepositMoney {
        CompletableFuture<String> deposit(DepositMoneyDTO dto);
    }
}
