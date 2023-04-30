package com.example.guitoylanguage.Model.Types;

import com.example.guitoylanguage.Model.Values.StringValue;
import com.example.guitoylanguage.Model.Values.Value;

public class StringType implements Type{
    public boolean equals(Type anotherType)
    {
        return anotherType instanceof StringType;
    }
    public Value defaultValue(){
        return new StringValue("");
    }

    public Type deepCopy(){
        return new StringType();
    }

    public String toString()
    {
        return "string";
    }
}
