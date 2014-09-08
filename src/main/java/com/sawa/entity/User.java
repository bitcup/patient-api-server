package com.sawa.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public abstract class User {

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String email;

    // todo: exclude from json returned
    @Getter
    @Setter
    private String password;

    // todo: status

}
