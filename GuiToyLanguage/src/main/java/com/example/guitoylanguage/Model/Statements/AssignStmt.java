package com.example.guitoylanguage.Model.Statements;

import com.example.guitoylanguage.Exceptions.MyException;
import com.example.guitoylanguage.Model.Expressions.Exp;
import com.example.guitoylanguage.Model.PrgState.PrgState;
import com.example.guitoylanguage.Model.Types.Type;
import com.example.guitoylanguage.Model.Values.Value;
import com.example.guitoylanguage.Model.utils.*;

public class AssignStmt implements  IStmt{
    String id;
    Exp exp;
    public AssignStmt(String x,Exp e)
    {
        id=x;
        exp=e;
    }
    @Override
    public String toString()
    {
        return id+"="+exp.toString();

    }
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk=state.getExeStack();
        MyIDictionary<String,Value> SymTbl=state.getSymTable();

    if (SymTbl.isDefined(id)){
        Value val=exp.eval(SymTbl,state.getHeap());
        Type typeId=(SymTbl.lookUp(id)).getType();
        if((val.getType()).equals(typeId))
        {
            SymTbl.update(id,val);
        }
        else
        {

            throw new MyException("declared type of variable "+id+" and type of the assigned expression do not match");
        }

    }
    else
        throw new MyException("the used variable" + id+" was not declared before");
    state.setSymTable(SymTbl);
    return null;
}
    public IStmt deepCopy(){return new AssignStmt(id,exp.deepCopy());}

    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String,Type> typeEnv) throws MyException
    {
        Type typevar=typeEnv.lookUp(id);
        Type typexp=exp.typeCheck(typeEnv);
        if(typevar.equals(typexp))
            return typeEnv;
        else
            throw new MyException("Assignment: left handside and right handside have different types");
    }
}