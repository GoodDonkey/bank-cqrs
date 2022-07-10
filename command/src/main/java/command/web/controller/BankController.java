package command.web.controller;

import command.usecase.Bank;
import command.usecase.dto.CreateAccountDTO;
import command.usecase.dto.CreateHolderDTO;
import command.usecase.dto.DepositMoneyDTO;
import command.usecase.dto.WithdrawMoneyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
public class BankController {
    private final Bank.CreateHolder createHolder;
    private final Bank.CreateAccount createAccount;
    private final Bank.DepositMoney depositMoney;
    private final Bank.WithdrawMoney withdrawMoney;
    
    @PostMapping("/holder")
    public CompletableFuture<String> createHolder(@RequestBody CreateHolderDTO dto) {
        return createHolder.createHolder(dto);
    }
    
    @PostMapping("/account")
    public CompletableFuture<String> createAccount(@RequestBody CreateAccountDTO dto) {
        return createAccount.createAccount(dto);
    }
    
    @PostMapping("/deposit")
    public CompletableFuture<String> deposit(@RequestBody DepositMoneyDTO dto) {
        return depositMoney.deposit(dto);
    }
    
    @PostMapping("/withdraw")
    public CompletableFuture<String> withdraw(@RequestBody WithdrawMoneyDTO dto) {
        return withdrawMoney.withdraw(dto);
    }
}
