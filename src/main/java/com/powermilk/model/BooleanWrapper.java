package com.powermilk.model;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BooleanWrapper {
    @Column(name = "value")
    @Type(type = "org.hibernate.type.TrueFalseType")
    private Boolean booleanValue;

    public BooleanWrapper() {
    }

    public BooleanWrapper(Boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public Boolean getBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(Boolean booleanValue) {
        this.booleanValue = booleanValue;
    }
}
