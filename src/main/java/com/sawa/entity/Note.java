package com.sawa.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * User: omar
 */
@EqualsAndHashCode
@ToString
public class Note {

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private Practitioner practitioner;

    @Getter
    @Setter
    private Date date;

    @Getter
    @Setter
    private String content;

}
