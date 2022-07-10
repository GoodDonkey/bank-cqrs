package com.bankcqrs.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class AccountCreated {
    private String accountId;
    private String holderId;
}
