package com.example.guitoylanguage.Model.Statements;

import com.example.guitoylanguage.Exceptions.MyException;
import com.example.guitoylanguage.Model.Expressions.Exp;
import com.example.guitoylanguage.Model.PrgState.PrgState;
import com.example.guitoylanguage.Model.Types.RefType;
import com.example.guitoylanguage.Model.Types.Type;
import com.example.guitoylanguage.Model.Values.RefValue;
import com.example.guitoylanguage.Model.Values.Value;
import com.example.guitoylanguage.Model.utils.MyIDictionary;
import com.example.guitoylanguage.Model.utils.MyIHeap;

public class HeapWriting implements IStmt{
    private final String varName;
    private final Exp expression;

    public HeapWriting(String varN, Exp e)
    {
        this.varName=varN;
        this.expression=e;
    }

    public PrgState execute(PrgState state) throws MyException
    {
        MyIDictionary<String, Value> symTable=state.getSymTable();
        MyIHeap heap=state.getHeap();
        if(!symTable.isDefined(varName))
            throw new MyException(String.format("%s not present",varName));
        Value val=symTable.lookUp(varName);
        if(!(val instanceof RefValue))
            throw new MyException(String.format("%s not of RefType",val));
        RefValue refV=(RefValue) val;
        Value evaluated= expression.eval(symTable,heap);
        if(!evaluated.getType().equals(refV.getLocationType()))
            throw new MyException(String.format("%s not of %s",evaluated,refV.getLocationType()));
        heap.update(refV.getAddress(),evaluated);
        state.setHeap(heap);
        return  null;
    }

    public IStmt deepCopy()
    {
        return new HeapWriting(varName,expression.deepCopy());
    }

    public String toString()
    {
        return String.format("WriteHeap(%s,%s)",varName,expression);
    }

    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String,Type> typeEnv) throws MyException
    {
        if(typeEnv.lookUp(varName).equals(new RefType(expression.typeCheck(typeEnv))))
            return typeEnv;
        else
            throw new MyException("WriteHeap: right hside and left hside different types");
    }
}
