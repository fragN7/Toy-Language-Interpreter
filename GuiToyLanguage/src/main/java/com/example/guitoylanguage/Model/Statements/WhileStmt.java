package com.example.guitoylanguage.Model.Statements;

import com.example.guitoylanguage.Exceptions.MyException;
import com.example.guitoylanguage.Model.Expressions.Exp;
import com.example.guitoylanguage.Model.PrgState.PrgState;
import com.example.guitoylanguage.Model.Types.BoolType;
import com.example.guitoylanguage.Model.Types.Type;
import com.example.guitoylanguage.Model.Values.BoolValue;
import com.example.guitoylanguage.Model.Values.Value;
import com.example.guitoylanguage.Model.utils.MyIDictionary;
import com.example.guitoylanguage.Model.utils.MyIStack;

public class WhileStmt implements IStmt{
    private final Exp exp;
    private final IStmt stmt;

    public WhileStmt(Exp e,IStmt i)
    {
        this.exp=e;
        this.stmt=i;
    }

    public PrgState execute(PrgState state) throws MyException
    {
        Value value=exp.eval(state.getSymTable(),state.getHeap());
        MyIStack<IStmt> stack=state.getExeStack();
        if(!value.getType().equals(new BoolType()))
            throw new MyException(String.format("%s is not of BoolType",value));
        BoolValue boolVal=(BoolValue) value;
        if(boolVal.getValue())
        {
            stack.push(this);
            stack.push(stmt);
        }
        return null;
    }

    public IStmt deepCopy()
    {
        return new WhileStmt(exp.deepCopy(),stmt.deepCopy());

    }

    public String toString()
    {
        return String.format("while(%s){%s}", exp,stmt);
    }

    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String,Type> typeEnv) throws MyException
    {
        Type typeExpr=exp.typeCheck(typeEnv);
        if(typeExpr.equals(new BoolType()))
        {
            stmt.typeCheck(typeEnv.deepCopy());
            return typeEnv;

        }
        else throw new MyException("Condition of WHILE does not have the type Bool.");
    }
}
