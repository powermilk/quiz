package com.powermilk.model;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BooleanWrapper {
    @Column(name = "value")
    @Type(type = "org.hibernate.type.TrueFalseType")
    private Boolean myBoolean;
}
