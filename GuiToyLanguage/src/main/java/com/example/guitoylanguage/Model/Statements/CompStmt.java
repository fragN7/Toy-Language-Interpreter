package com.example.guitoylanguage.Model.Statements;

import com.example.guitoylanguage.Exceptions.MyException;
import com.example.guitoylanguage.Model.PrgState.PrgState;
import com.example.guitoylanguage.Model.Types.Type;
import com.example.guitoylanguage.Model.utils.MyIDictionary;
import com.example.guitoylanguage.Model.utils.MyIStack;
public class CompStmt implements IStmt {

    IStmt first;
    IStmt snd;
    public CompStmt(IStmt f, IStmt s)
    {
        first=f;
        snd=s;
    }

    @Override
    public String toString(){
    return "("+first.toString()+";"+snd.toString()+")";
    }

    public PrgState execute(PrgState state) throws MyException{
        MyIStack<IStmt> stk=state.getExeStack();
        stk.push(snd);
        stk.push(first);
        state.setExeStack(stk);
        return null;
    }

    public IStmt deepCopy(){return new CompStmt(first.deepCopy(),snd.deepCopy());}


    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String,Type> typeEnv) throws MyException
    {
        return snd.typeCheck(first.typeCheck(typeEnv));
    }
}
