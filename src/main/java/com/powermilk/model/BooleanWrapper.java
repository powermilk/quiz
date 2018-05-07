package com.powermilk.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BooleanWrapper {
    @Column(name = "value")
    @Type(type = "org.hibernate.type.TrueFalseType")
    private boolean booleanValue;

    public BooleanWrapper() {
    }

    public BooleanWrapper(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public boolean getBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(booleanValue)
                .build();
    }

    @org.jetbrains.annotations.Contract(value = "null -> false", pure = true)
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final BooleanWrapper booleanWrapper = (BooleanWrapper) obj;
        return new EqualsBuilder()
                .append(booleanValue, booleanWrapper.booleanValue)
                .build();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
