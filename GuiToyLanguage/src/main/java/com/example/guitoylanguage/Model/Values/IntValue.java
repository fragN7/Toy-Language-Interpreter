package com.example.guitoylanguage.Model.Values;

import com.example.guitoylanguage.Model.Types.IntType;
import com.example.guitoylanguage.Model.Types.Type;
public class IntValue implements Value{
    private final int  value;
    public IntValue(int v)
    {
        this.value=v;
    }
    public Type getType()
    {
        return new IntType();
    }
    @Override
    public boolean equals(Object anotherValue)
    {
        if(!(anotherValue instanceof IntValue))
            return false;
        IntValue castValue=(IntValue) anotherValue;
        return this.value==castValue.value;
    }

    public Value deepCopy(){
        return new IntValue(value);
    }
    public int getValue(){
        return this.value;
    }
    public String toString(){
        return String.format("%d",this.value);
    }
}
