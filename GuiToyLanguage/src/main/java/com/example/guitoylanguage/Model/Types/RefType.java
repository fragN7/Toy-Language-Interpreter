package com.example.guitoylanguage.Model.Types;

import com.example.guitoylanguage.Model.Values.RefValue;
import com.example.guitoylanguage.Model.Values.Value;

public class RefType implements Type {
    private final Type inner;

    public RefType(Type inner) {
        this.inner = inner;
    }

    public Type getInner() {
        return this.inner;
    }

    public boolean equals(Type anotherType) {
        if (anotherType instanceof RefType)
            return inner.equals(((RefType) anotherType).getInner());
        else
            return false;
    }

    public Value defaultValue() {
        return new RefValue(0, inner);
    }

    public Type deepCopy() {
        return new RefType(inner.deepCopy());
    }

    public String toString()
    {
        return String.format("Ref(%s)",inner);
    }


}
