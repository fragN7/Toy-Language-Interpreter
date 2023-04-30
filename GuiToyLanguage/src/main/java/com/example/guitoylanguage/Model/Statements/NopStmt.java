package com.example.guitoylanguage.Model.Statements;

import com.example.guitoylanguage.Exceptions.MyException;
import com.example.guitoylanguage.Model.PrgState.PrgState;
import com.example.guitoylanguage.Model.Types.Type;
import com.example.guitoylanguage.Model.utils.MyIDictionary;

public class NopStmt implements IStmt{

    @Override
    public String toString() {
            return "NopStmt";
    }

    public PrgState execute(PrgState  state) throws MyException
    {
        return null;
    }
    public IStmt deepCopy()
    {
            return new NopStmt();
    }

    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String,Type> typeEnv) throws MyException
    {
        return typeEnv;
    }
}
