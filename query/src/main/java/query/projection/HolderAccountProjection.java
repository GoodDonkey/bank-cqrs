package query.projection;

import query.entity.HolderAccountSummary;
import query.repository.HolderAccountJpaRepository;
import com.bankcqrs.events.AccountCreated;
import com.bankcqrs.events.HolderCreated;
import com.bankcqrs.events.MoneyDeposited;
import com.bankcqrs.events.MoneyWithdrawn;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.NoSuchElementException;

@Component
@AllArgsConstructor
@Slf4j
public class HolderAccountProjection {
    private final HolderAccountJpaRepository repository;
    
    @EventHandler
    protected void on(HolderCreated event, @Timestamp Instant instant) {
        log.debug("projecting event {}, timestamp: {}", event, instant.toString());
        HolderAccountSummary accountSummary = HolderAccountSummary.builder().holderId(event.getHolderId())
                                                                  .name(event.getHolderName()).address(event.getAddress())
                                                                  .phoneNumber(event.getPhoneNumber()).totalBalance(0L)
                                                                  .accountCount(0).build();
        repository.save(accountSummary);
    }
    
    @EventHandler
    protected void on(AccountCreated event, @Timestamp Instant instant){
        log.debug("projecting event {}, timestamp: {}", event, instant.toString());
        HolderAccountSummary accountSummary = getHolderAccountSummary(event.getHolderId());
        accountSummary.setAccountCount(accountSummary.getAccountCount() + 1);
        repository.save(accountSummary);
    }
    
    @EventHandler
    protected void on(MoneyDeposited event, @Timestamp Instant instant){
        log.debug("projecting event {}, timestamp: {}", event, instant.toString());
        HolderAccountSummary accountSummary = getHolderAccountSummary(event.getHolderId());
        accountSummary.setTotalBalance(accountSummary.getTotalBalance() + event.getAmount());
        repository.save(accountSummary);
    }
    
    @EventHandler
    protected void on(MoneyWithdrawn event, @Timestamp Instant instant){
        log.debug("projecting event {}, timestamp: {}", event, instant.toString());
        HolderAccountSummary accountSummary = getHolderAccountSummary(event.getHolderId());
        accountSummary.setTotalBalance(accountSummary.getTotalBalance() - event.getAmount());
        repository.save(accountSummary);
    }
    
    private HolderAccountSummary getHolderAccountSummary(String holderId) {
        return repository.findByHolderId(holderId)
                         .orElseThrow(() -> new NoSuchElementException("계좌 소유주가 존재하지 않습니다."));
    }
}
