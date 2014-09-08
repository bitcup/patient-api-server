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
public class Care {

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private Patient patient;

    @Getter
    @Setter
    private String facilityName;

    @Getter
    @Setter
    private String location;

    @Getter
    @Setter
    private boolean overnight;

    @Getter
    @Setter
    private Date start;

    @Getter
    @Setter
    private Date end;

    @Getter
    @Setter
    private String reason;

    @Getter
    @Setter
    private String note;

}
