package com.example.guitoylanguage.Model.Statements;

import com.example.guitoylanguage.Exceptions.MyException;
import com.example.guitoylanguage.Model.PrgState.PrgState;
import com.example.guitoylanguage.Model.Types.Type;
import com.example.guitoylanguage.Model.Values.Value;
import com.example.guitoylanguage.Model.utils.MyDictionary;
import com.example.guitoylanguage.Model.utils.MyIDictionary;
import com.example.guitoylanguage.Model.utils.MyIStack;
import com.example.guitoylanguage.Model.utils.MyStack;

import java.util.Map;

public class ForkStmt implements IStmt{

    private final IStmt statement;

    public ForkStmt(IStmt stm)
    {
        this.statement=stm;
    }

    @Override
    public PrgState execute(PrgState state)
        throws MyException
    {
        MyIStack<IStmt> newStack=new MyStack<>();
        MyIDictionary<String, Value> newSymTable = new MyDictionary<>();
        newStack.push(statement);
        for (Map.Entry<String, Value> entry: state.getSymTable().getContent().entrySet()) {
            newSymTable.put(entry.getKey(), entry.getValue().deepCopy());
        }
        //newPrgState.sID(); // initiate ID-s here instead of in PrgState constructor because it would initiate ID for every example in the menu and they won't be consecutive
        return new PrgState(newStack,newSymTable,state.getOut(),state.getFileTable(),state.getHeap(), state.getLockTable());
    }

    public IStmt deepCopy()
    {
        return new ForkStmt(statement.deepCopy());
    }

    public String toString()
    {
        return String.format("Fork(%s)",statement.toString());
    }

    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String,Type> typeEnv) throws MyException
    {
        statement.typeCheck(typeEnv);
        return typeEnv;
    }

}
