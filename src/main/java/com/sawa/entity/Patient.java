package com.sawa.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * User: omar
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Patient extends User {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Date dob;

    @Getter
    @Setter
    private String privateAuthKey;

}
