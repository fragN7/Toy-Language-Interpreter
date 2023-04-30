package com.example.guitoylanguage.Model.Expressions;
import com.example.guitoylanguage.Exceptions.MyException;
import com.example.guitoylanguage.Model.Types.Type;
import com.example.guitoylanguage.Model.Values.Value;
import com.example.guitoylanguage.Model.utils.MyIDictionary;
import com.example.guitoylanguage.Model.utils.MyIHeap;

public interface Exp {

    Type typeCheck(MyIDictionary<String,Type> typeEnv) throws MyException;
    Value eval(MyIDictionary<String,Value> symTable, MyIHeap heap) throws MyException;
    Exp deepCopy();
}
