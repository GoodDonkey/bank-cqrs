package com.bankcqrs.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.axonframework.serialization.Revision;

@AllArgsConstructor
@Getter
@ToString
@Revision("1.0")
public class HolderCreated {
    private String holderId;
    private String holderName;
    private String phoneNumber;
    private String address;
    private String company;
}
