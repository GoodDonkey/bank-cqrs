package com.bankcqrs.query;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
public class LoanLimitQuery {
    private String holderId;
    private Long balance;
}
