package com.example.guitoylanguage.Model.Statements;

import com.example.guitoylanguage.Exceptions.MyException;
import com.example.guitoylanguage.Model.PrgState.PrgState;
import com.example.guitoylanguage.Model.Types.Type;
import com.example.guitoylanguage.Model.Values.Value;
import com.example.guitoylanguage.Model.utils.MyIDictionary;

public class VarDeclStmt implements IStmt {
    String name;
    Type type;
    public VarDeclStmt(String t,Type x){
        type=x;
        name=t;

    }

    @Override
    public String toString() {
        return String.format("%s %s",type.toString(),name);
    }
    public PrgState execute(PrgState state) throws MyException{
        MyIDictionary<String,Value> symTable=state.getSymTable();
        if(symTable.isDefined(name))
        {
            throw new MyException("Variable"+ name+" already taken");
        }
        symTable.put(name,type.defaultValue());
        state.setSymTable(symTable);
        return null;
    }

    public IStmt deepCopy(){
        return new VarDeclStmt(name,type);

    }

    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String,Type> typeEnv) throws MyException
    {
        typeEnv.put(name,type);
        return typeEnv;
    }
}
