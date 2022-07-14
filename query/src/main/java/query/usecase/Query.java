package query.usecase;

import query.entity.HolderAccountSummary;

public abstract class Query {
    
    public interface ResetHolderAccountSummaries {
        void reset();
    }
    
    public interface GetAccountInfo {
        HolderAccountSummary getAccountInfo(String holderId);
    }
}
