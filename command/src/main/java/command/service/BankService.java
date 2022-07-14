package command.service;

import command.commands.CreateAccountCommand;
import command.commands.CreateHolderCommand;
import command.commands.DepositMoneyCommand;
import command.commands.WithdrawMoneyCommand;
import command.usecase.Bank;
import command.usecase.dto.CreateAccountDTO;
import command.usecase.dto.CreateHolderDTO;
import command.usecase.dto.DepositMoneyDTO;
import command.usecase.dto.WithdrawMoneyDTO;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class BankService implements Bank.CreateHolder, Bank.CreateAccount, Bank.DepositMoney, Bank.WithdrawMoney {
    
    private final CommandGateway commandGateway;
    
    @Override
    public CompletableFuture<String> createHolder(CreateHolderDTO dto) {
        return commandGateway.send(new CreateHolderCommand(UUID.randomUUID().toString(),
                                                           dto.getHolderName(),
                                                           dto.getPhoneNumber(),
                                                           dto.getAddress(),
                                                           dto.getCompany()));
    }
    
    @Override
    public CompletableFuture<String> createAccount(CreateAccountDTO dto) {
        return commandGateway.send(new CreateAccountCommand(UUID.randomUUID().toString(), dto.getHolderId()));
    }
    
    @Override
    public CompletableFuture<String> deposit(DepositMoneyDTO dto) {
        return commandGateway.send(new DepositMoneyCommand(dto.getAccountId(), dto.getHolderId(), dto.getAmount()));
    }
    
    @Override
    public CompletableFuture<String> withdraw(WithdrawMoneyDTO dto) {
        return commandGateway.send(new WithdrawMoneyCommand(dto.getAccountId(), dto.getHolderId(), dto.getAmount()));
    }
}
