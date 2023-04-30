package com.example.guitoylanguage.Model.Types;

import com.example.guitoylanguage.Model.Values.BoolValue;
import com.example.guitoylanguage.Model.Values.Value;
public class BoolType implements Type{
    public boolean equals(Type anotherType)
    {
        return anotherType instanceof BoolType;
    }

    @Override
    public Value defaultValue() {
        return new BoolValue(false);
    }

    public Type deepCopy()
    {
        return new BoolType();
    }

    public String toString(){
        return "bool";
    }
}
