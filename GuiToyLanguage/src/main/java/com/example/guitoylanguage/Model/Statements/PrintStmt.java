package com.example.guitoylanguage.Model.Statements;

import com.example.guitoylanguage.Exceptions.MyException;
import com.example.guitoylanguage.Model.Expressions.Exp;
import com.example.guitoylanguage.Model.PrgState.PrgState;
import com.example.guitoylanguage.Model.Types.Type;
import com.example.guitoylanguage.Model.Values.Value;
import com.example.guitoylanguage.Model.utils.MyIDictionary;
import com.example.guitoylanguage.Model.utils.MyIList;

public class PrintStmt implements  IStmt{
    Exp exp;
    public PrintStmt(Exp e){
        exp=e;

    }
    @Override
    public String toString(){
        return "print("+exp.toString()+")";
    }
    public PrgState execute(PrgState state) throws MyException {
        MyIList<Value> out=state.getOut();
        out.add(exp.eval(state.getSymTable(),state.getHeap()));
        state.setOut(out);
        return null;


    }
    public IStmt deepCopy(){return new PrintStmt(exp.deepCopy());}

    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String,Type> typeEnv) throws MyException
    {
        exp.typeCheck(typeEnv);
        return typeEnv;
    }

}
