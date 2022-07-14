package query.usecase;

import com.bankcqrs.query.LoanLimitResult;
import query.entity.HolderAccountSummary;
import reactor.core.publisher.Flux;

import java.util.List;

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
    
    public interface GetAccountInfoScatterGather {
        List<LoanLimitResult> getAccountInfoScatterGather(String holderId);
    }
}
