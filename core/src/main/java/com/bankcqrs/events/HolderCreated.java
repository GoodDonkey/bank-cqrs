package com.bankcqrs.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class HolderCreated {
    private String holderId;
    private String holderName;
    private String phoneNumber;
    private String address;
}
