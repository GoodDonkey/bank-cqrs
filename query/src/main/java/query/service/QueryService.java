package query.service;

import query.usecase.Query;

public abstract class QueryService implements Query.ResetHolderAccountSummaries,
                                              Query.GetAccountInfo,
                                              Query.GetAccountInfoSubscription{
}
