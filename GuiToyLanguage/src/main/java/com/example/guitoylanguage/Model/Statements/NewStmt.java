package com.example.guitoylanguage.Model.Statements;

import com.example.guitoylanguage.Exceptions.MyException;
import com.example.guitoylanguage.Model.PrgState.PrgState;
import com.example.guitoylanguage.Model.Types.Type;
import com.example.guitoylanguage.Model.Values.Value;
import com.example.guitoylanguage.Model.Values.RefValue;
import com.example.guitoylanguage.Model.Types.RefType;
import com.example.guitoylanguage.Model.utils.MyIDictionary;
import com.example.guitoylanguage.Model.utils.MyIHeap;
import com.example.guitoylanguage.Model.Expressions.Exp;

public class NewStmt implements IStmt{
    private final String varName;
    private final Exp expression;

    public NewStmt(String varName, Exp expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap heap = state.getHeap();
        if (symTable.isDefined(varName)) {
            Value varValue = symTable.lookUp(varName);
            if ((varValue.getType() instanceof RefType)) {
                Value evaluated = expression.eval(symTable, heap);
                Type locationType = ((RefValue) varValue).getLocationType();
                if (locationType.equals(evaluated.getType())) {
                    int newPosition = heap.add(evaluated);
                    symTable.put(varName, new RefValue(newPosition, locationType));
                    state.setSymTable(symTable);
                    state.setHeap(heap);
                } else
                    throw new MyException(String.format("%s does not match %s", varName, evaluated.getType()));
            } else {
                throw new MyException(String.format("%s does not match RefType", varName));
            }
        } else {
            throw new MyException(String.format("%s does not match symTable", varName));
        }
        return null;
    }

    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookUp(varName);
        Type typeExpr = expression.typeCheck(typeEnv);
        if (typeVar.equals(new RefType(typeExpr)))
            return typeEnv;
        else
            throw new MyException("NEW statement: right hand side and left hand side don't match.");
    }

    public IStmt deepCopy() {
        return new NewStmt(varName, expression.deepCopy());
    }

    public String toString() {
        return String.format("New(%s, %s)", varName, expression);
    }
}
