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

public class IfStmt implements  IStmt{
    Exp exp;
    IStmt thenS;
    IStmt elseS;
    public IfStmt(Exp e,IStmt t, IStmt el)
    {
        exp=e;
        thenS=t;
        elseS=el;
    }
    @Override
    public String toString(){
        return "(IF("+exp.toString()+")THEN("+thenS.toString()+")ELSE("+elseS.toString()+"))";
    }
    public PrgState execute(PrgState state) throws MyException
    {

        Value result=this.exp.eval(state.getSymTable(),state.getHeap());
        if(result instanceof BoolValue boolResult)
        {
            IStmt statement;
            if(boolResult.getValue())
                {
                    statement=thenS;
                }
            else
                {
                    statement=elseS;
                }
            MyIStack<IStmt> stk=state.getExeStack();
            stk.push(statement);
            state.setExeStack(stk);
            return null;

        }
        else
        {
       throw new MyException("Please provide a boolean expression in an if stmt");
         }
    }
    public  IStmt deepCopy(){return new IfStmt(exp.deepCopy(),thenS.deepCopy(),elseS.deepCopy());}
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String,Type> typeEnv) throws MyException
    {
        Type typeExpr= exp.typeCheck(typeEnv);
        if(typeExpr.equals(new BoolType()))
        {
            thenS.typeCheck(typeEnv.deepCopy());
            elseS.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        }else throw new MyException("The condition of IF does not have type bool");
    }


}
