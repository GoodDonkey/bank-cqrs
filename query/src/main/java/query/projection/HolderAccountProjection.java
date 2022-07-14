package query.projection;

import com.bankcqrs.events.AccountCreated;
import com.bankcqrs.events.HolderCreated;
import com.bankcqrs.events.MoneyDeposited;
import com.bankcqrs.events.MoneyWithdrawn;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.AllowReplay;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.axonframework.eventhandling.Timestamp;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import query.entity.HolderAccountSummary;
import query.query.AccountQuery;
import query.repository.HolderAccountJpaRepository;

import java.time.Instant;
import java.util.NoSuchElementException;

@Component
@AllArgsConstructor
@Slf4j
@ProcessingGroup("accounts")
public class HolderAccountProjection {
    private final HolderAccountJpaRepository repository;
    
    @EventHandler
    @AllowReplay
    protected void on(HolderCreated event, @Timestamp Instant instant) {
        log.debug("projecting event {}, timestamp: {}", event, instant.toString());
        HolderAccountSummary accountSummary = HolderAccountSummary.builder().holderId(event.getHolderId())
                                                                  .name(event.getHolderName()).address(event.getAddress())
                                                                  .phoneNumber(event.getPhoneNumber()).totalBalance(0L)
                                                                  .accountCount(0).build();
        repository.save(accountSummary);
    }
    
    @EventHandler
    @AllowReplay
    protected void on(AccountCreated event, @Timestamp Instant instant){
        log.debug("projecting event {}, timestamp: {}", event, instant.toString());
        HolderAccountSummary accountSummary = getHolderAccountSummary(event.getHolderId());
        accountSummary.setAccountCount(accountSummary.getAccountCount() + 1);
        repository.save(accountSummary);
    }
    
    @EventHandler
    @AllowReplay
    protected void on(MoneyDeposited event, @Timestamp Instant instant){
        log.debug("projecting event {}, timestamp: {}", event, instant.toString());
        HolderAccountSummary accountSummary = getHolderAccountSummary(event.getHolderId());
        accountSummary.setTotalBalance(accountSummary.getTotalBalance() + event.getAmount());
        repository.save(accountSummary);
    }
    
    @EventHandler
    @AllowReplay
    protected void on(MoneyWithdrawn event, @Timestamp Instant instant){
        log.debug("projecting event {}, timestamp: {}", event, instant.toString());
        HolderAccountSummary accountSummary = getHolderAccountSummary(event.getHolderId());
        accountSummary.setTotalBalance(accountSummary.getTotalBalance() - event.getAmount());
        repository.save(accountSummary);
    }
    
    @QueryHandler
    public HolderAccountSummary on(AccountQuery query) {
        log.debug("query={}", query);
        return repository.findByHolderId(query.getHolderId())
                .orElse(null);
    }
    
    @ResetHandler
    private void reset() {
        log.debug("accounts reset triggered");
        repository.deleteAll();
    }
    
    private HolderAccountSummary getHolderAccountSummary(String holderId) {
        return repository.findByHolderId(holderId)
                         .orElseThrow(() -> new NoSuchElementException("계좌 소유주가 존재하지 않습니다."));
    }
}
