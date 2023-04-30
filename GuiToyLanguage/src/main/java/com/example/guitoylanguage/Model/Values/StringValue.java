package com.example.guitoylanguage.Model.Values;

import com.example.guitoylanguage.Model.Types.StringType;
import com.example.guitoylanguage.Model.Types.Type;

public class StringValue implements Value{

    private final String value;

    public StringValue(String value)
    {
        this.value=value;
    }

    public Type getType(){
        return new StringType();
    }

    public boolean equals(Object anotherValue)
    {
        if(!(anotherValue instanceof StringValue))
            return false;
        StringValue castValue=(StringValue) anotherValue;
        return this.value.equals(castValue.value);

    }

    public Value deepCopy()
    {
        return new StringValue(value);
    }

    public String getValue()
    {
        return this.value;
    }

    public String  toString()
    {
        return "\"" + this.value + "\"";
    }




}
