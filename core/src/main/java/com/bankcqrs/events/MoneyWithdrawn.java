package com.bankcqrs.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class MoneyWithdrawn {
    private String accountId;
    private String holderId;
    private Long amount;
}
