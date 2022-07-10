package command.web.controller;

import command.usecase.Bank;
import command.usecase.dto.CreateAccountDTO;
import command.usecase.dto.CreateHolderDTO;
import command.usecase.dto.DepositMoneyDTO;
import command.usecase.dto.WithdrawMoneyDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BankController {
    private final Bank.CreateHolder createHolder;
    private final Bank.CreateAccount createAccount;
    private final Bank.DepositMoney depositMoney;
    private final Bank.WithdrawMoney withdrawMoney;
    
    @PostMapping("/holder")
    public CompletableFuture<String> createHolder(@RequestBody @Valid CreateHolderDTO dto) {
        log.debug("dto={}", dto);
        return createHolder.createHolder(dto);
    }
    
    @PostMapping("/account")
    public CompletableFuture<String> createAccount(@RequestBody @Valid CreateAccountDTO dto) {
        log.debug("dto={}", dto);
        return createAccount.createAccount(dto);
    }
    
    @PostMapping("/deposit")
    public CompletableFuture<String> deposit(@RequestBody @Valid DepositMoneyDTO dto) {
        log.debug("dto={}", dto);
        return depositMoney.deposit(dto);
    }
    
    @PostMapping("/withdraw")
    public CompletableFuture<String> withdraw(@RequestBody @Valid WithdrawMoneyDTO dto) {
        log.debug("dto={}", dto);
        return withdrawMoney.withdraw(dto);
    }
}
