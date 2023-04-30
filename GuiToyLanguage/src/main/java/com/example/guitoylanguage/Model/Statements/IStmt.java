package com.example.guitoylanguage.Model.Statements;

import com.example.guitoylanguage.Exceptions.MyException;
import com.example.guitoylanguage.Model.PrgState.PrgState;
import com.example.guitoylanguage.Model.Types.Type;
import com.example.guitoylanguage.Model.utils.MyIDictionary;

public interface IStmt {
     PrgState execute(PrgState state) throws MyException;
     String toString();
    IStmt deepCopy();

    MyIDictionary<String, Type> typeCheck(MyIDictionary<String,Type> typeEnv) throws MyException;

}
