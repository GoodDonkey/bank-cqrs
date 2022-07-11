package query.service;

import lombok.RequiredArgsConstructor;
import org.axonframework.config.Configuration;
import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpringQueryService extends QueryService {
    
    private final Configuration configuration;
    
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
