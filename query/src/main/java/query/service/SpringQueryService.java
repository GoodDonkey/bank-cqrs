package query.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.Configuration;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.stereotype.Service;
import query.entity.HolderAccountSummary;
import query.query.AccountQuery;
import reactor.core.publisher.Flux;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpringQueryService extends QueryService {
    
    private final Configuration configuration;
    private final QueryGateway queryGateway;
    
    @Override
    public Flux<HolderAccountSummary> getAccountInfoSubscription(String holderId) {
        AccountQuery query = new AccountQuery(holderId);
        log.debug("query={}", query);
    
        SubscriptionQueryResult<HolderAccountSummary, HolderAccountSummary> queryResult = queryGateway.subscriptionQuery(
                query,
                ResponseTypes.instanceOf(HolderAccountSummary.class),
                ResponseTypes.instanceOf(HolderAccountSummary.class));
        return Flux.create(emitter -> {
            queryResult.initialResult().subscribe(emitter::next);
            queryResult.updates()
                    .doOnNext(holder -> {
                        log.debug("doOnNext: {}, isCanceled: {}", holder, emitter.isCancelled());
                        if (emitter.isCancelled()) {
                            queryResult.close();
                        }
                    })
                    .doOnComplete(emitter::complete)
                    .subscribe(emitter::next);
        });
    }
    
    @Override
    public HolderAccountSummary getAccountInfo(String holderId) {
        AccountQuery query = new AccountQuery(holderId);
        log.debug("query={}", query);
        return queryGateway.query(query, ResponseTypes.instanceOf(HolderAccountSummary.class)).join();
    }
    
    @Override
    public void reset() {
        configuration.eventProcessingConfiguration()
                .eventProcessorByProcessingGroup("accounts", TrackingEventProcessor.class)
                .ifPresent(trackingEventProcessor -> {
                    trackingEventProcessor.shutDown(); // 작업 중단
                    trackingEventProcessor.resetTokens(); // 토큰 초기화
                    trackingEventProcessor.start(); // replay 시작
                });
    }
}
