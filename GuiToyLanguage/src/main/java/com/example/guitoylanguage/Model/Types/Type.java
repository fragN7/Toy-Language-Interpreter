package com.example.guitoylanguage.Model.Types;

import com.example.guitoylanguage.Model.Values.Value;
public interface Type {
    boolean equals(Type anotherType);
    Value defaultValue();
    Type deepCopy();
    public String toString();
}
