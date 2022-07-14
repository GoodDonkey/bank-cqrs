package com.bankcqrs.query;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Builder
@Getter
public class LoanLimitResult {
    private String holderId;
    private String bankName;
    private Long balance;
    private Long loanLimit;
}
