package com.example.guitoylanguage.Model.Values;

import com.example.guitoylanguage.Model.Types.BoolType;
import com.example.guitoylanguage.Model.Types.Type;
public class BoolValue implements Value {
    private final boolean value;

    public BoolValue(boolean v) {
        this.value = v;
    }

    public boolean equals(Object anotherValue) {
        if (!(anotherValue instanceof BoolValue))
            return false;
        BoolValue castValue = (BoolValue) anotherValue;
        return this.value == castValue.value;
    }

    public Type getType() {
        return new BoolType();
    }

    public String toString() {
        return this.value ? "true" : "false";
    }

    public Value deepCopy() {
        return new BoolValue(value);
    }

    public boolean getValue() {
        return this.value;

    }
}
