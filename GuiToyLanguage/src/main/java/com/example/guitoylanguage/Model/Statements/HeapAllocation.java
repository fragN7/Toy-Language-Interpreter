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

public class HeapAllocation implements IStmt {
    private final String varName;
    private final Exp exp;

    public HeapAllocation(String varName, Exp ex) {
        this.varName = varName;
        this.exp = ex;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap heap = state.getHeap();
        if (symTable.isDefined(varName)) {
            Value varValue = symTable.lookUp(varName);
            if ((varValue.getType() instanceof RefType)) {
                Value evaluated = exp.eval(symTable, heap);
                Type locationType = ((RefValue) varValue).getLocationType();
                if (locationType.equals(evaluated.getType())) {
                    int newPosition = heap.add(evaluated);
                    symTable.put(varName, new RefValue(newPosition, locationType));
                    state.setSymTable(symTable);
                    state.setHeap(heap);
                } else
                    throw new MyException(String.format("%s not of %s", varName, evaluated.getType()));
            } else {
                throw new MyException(String.format("%s in not of RefType", varName));
            }
        } else {
            throw new MyException(String.format("%s not in symTable", varName));
        }
        return null;
    }

    public IStmt deepCopy()
    {
        return new HeapAllocation(varName,exp.deepCopy());
    }

    public String toString()
    {
        return String.format("New(%s,%s)",varName,exp);
    }

    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String,Type> typeEnv) throws MyException
    {
        Type typevar=typeEnv.lookUp(varName);
        Type typeexp=exp.typeCheck(typeEnv);
        if(typevar.equals(new RefType(typeexp)))
            return typeEnv;
        else
            throw new MyException("Heap allocation statement: right hand side and left hand side have different types");
    }
}
