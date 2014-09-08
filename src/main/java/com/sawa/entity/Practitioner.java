package com.sawa.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * User: omar
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Practitioner extends User {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String phone;

    @Getter
    @Setter
    private String specialty;

    @Getter
    @Setter
    private Care care;

}
