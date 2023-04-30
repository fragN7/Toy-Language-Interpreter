package com.example.guitoylanguage.Model.Expressions;

import com.example.guitoylanguage.Exceptions.MyException;
import com.example.guitoylanguage.Model.Types.Type;
import com.example.guitoylanguage.Model.Values.Value;
import com.example.guitoylanguage.Model.utils.MyIDictionary;
import com.example.guitoylanguage.Model.utils.MyIHeap;

public class VarExp implements Exp{
    String key;
    public VarExp(String key)
    {this.key=key;}
    public Value eval(MyIDictionary<String,Value> symTable, MyIHeap heap) throws MyException
    {
        return symTable.lookUp(key);
    }
    public Exp deepCopy(){
        return new VarExp(key);
    }

    public String toString(){
        return key;
    }

    public Type typeCheck(MyIDictionary<String,Type> typeEnv) throws MyException
    {
        return typeEnv.lookUp(key);
    }
}
