package com.bankcqrs.jeju.component;

import com.bankcqrs.query.LoanLimitQuery;
import com.bankcqrs.query.LoanLimitResult;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AccountLoanComponent {
    
    @QueryHandler
    private LoanLimitResult on(LoanLimitQuery query) {
        log.debug("query={}", query);
        // 잔고의 120% 까지 대출할 수 있다고 가정.
        return LoanLimitResult.builder()
                       .holderId(query.getHolderId())
                       .bankName("JejuBank")
                       .loanLimit(Double.valueOf(query.getBalance() * 1.2).longValue())
                       .balance(query.getBalance())
                       .build();
    }
}
