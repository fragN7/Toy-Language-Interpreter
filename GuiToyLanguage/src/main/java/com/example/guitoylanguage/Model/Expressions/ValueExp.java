package com.example.guitoylanguage.Model.Expressions;

import com.example.guitoylanguage.Exceptions.MyException;
import com.example.guitoylanguage.Model.Types.Type;
import com.example.guitoylanguage.Model.Values.Value;
import com.example.guitoylanguage.Model.utils.MyIDictionary;
import com.example.guitoylanguage.Model.utils.MyIHeap;

public class ValueExp implements Exp{
    Value value;
    public ValueExp(Value value){this.value=value;}
    public Value eval(MyIDictionary<String,Value> symTable, MyIHeap heap)
    {
        return this.value;
    }

    public Exp deepCopy(){return new ValueExp(value);}

    public String toString(){
        return this.value.toString();
    }

    public Type typeCheck(MyIDictionary<String,Type> typeEnv) throws MyException
    {
        return value.getType();
    }
}
