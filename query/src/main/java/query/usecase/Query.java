package query.usecase;

import query.entity.HolderAccountSummary;
import reactor.core.publisher.Flux;

public abstract class Query {
    
    public interface ResetHolderAccountSummaries {
        void reset();
    }
    
    public interface GetAccountInfo {
        HolderAccountSummary getAccountInfo(String holderId);
    }
    
    public interface GetAccountInfoSubscription {
        Flux<HolderAccountSummary> getAccountInfoSubscription(String holderId);
    }
}
