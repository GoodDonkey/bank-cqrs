package command.config;

import command.aggregate.Account;
import org.axonframework.common.caching.Cache;
import org.axonframework.common.caching.WeakReferenceCache;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.eventsourcing.*;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.snapshotting.SnapshotFilter;
import org.axonframework.modelling.command.Repository;
import org.axonframework.springboot.autoconfig.AxonAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(AxonAutoConfiguration.class)
public class AxonConfig {

//    @Bean
//    CommandBus commandBus(TransactionManager transactionManager) {
//        return SimpleCommandBus.builder().transactionManager(transactionManager).build();
//    }
    
    @Bean
    public AggregateFactory<Account> aggregateFactory() {
        return new GenericAggregateFactory<>(Account.class);
    }

    @Bean
    public Snapshotter snapshotter(EventStore eventStore, TransactionManager transactionManager) {
        return AggregateSnapshotter.builder().eventStore(eventStore).aggregateFactories(aggregateFactory())
                                   .transactionManager(transactionManager).build();
    }

    // CachingEventSourcingRepository 에 주입하는 Cache
    @Bean
    public Cache cache() {
        return new WeakReferenceCache();
    }

    @Bean(name = "accountSnapshotFilter")
    public SnapshotFilter accountSnapshotFilter() {
        return snapshotData -> true;
    }

    @Bean(name = "accountSnapshotTriggerDefinition")
    public SnapshotTriggerDefinition snapshotTriggerDefinition(Snapshotter snapshotter) {
        final int SNAPSHOT_THRESHOLD = 5;
        return new EventCountSnapshotTriggerDefinition(snapshotter, SNAPSHOT_THRESHOLD);
    }

    @Bean(name = "accountRepository")
    public Repository<Account> accountRepository(EventStore eventStore,
                                                 SnapshotTriggerDefinition snapshotTriggerDefinition, Cache cache) {
        return CachingEventSourcingRepository.builder(Account.class)
                                             .eventStore(eventStore)
                                             .snapshotTriggerDefinition(snapshotTriggerDefinition)
                                             .cache(cache)
                                             .build();
    }
}
