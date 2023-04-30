package com.example.guitoylanguage.Model.Statements;

import com.example.guitoylanguage.Exceptions.MyException;
import com.example.guitoylanguage.Model.PrgState.PrgState;
import com.example.guitoylanguage.Model.Types.Type;
import com.example.guitoylanguage.Model.Types.IntType;
import com.example.guitoylanguage.Model.Values.Value;
import com.example.guitoylanguage.Model.Values.IntValue;
import com.example.guitoylanguage.Model.utils.MyIDictionary;
import com.example.guitoylanguage.Model.utils.MyILockTable;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewLockStmt implements IStmt{
    private final String var;
    private static final Lock lock = new ReentrantLock();

    public NewLockStmt(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        MyILockTable lockTable = state.getLockTable();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        int freeLocation = lockTable.getFreeValue();
        lockTable.put(freeLocation, -1);
        if (symTable.isDefined(var) && symTable.lookUp(var).getType().equals(new IntType()))
            symTable.update(var, new IntValue(freeLocation));
        else
            throw new MyException("Variable not declared!");
        lock.unlock();
        return null;
    }

    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (typeEnv.lookUp(var).equals(new IntType()))
            return typeEnv;
        else
            throw new MyException("Var does not match int type!");
    }

    public IStmt deepCopy() {
        return new NewLockStmt(var);
    }

    public String toString() {
        return String.format("newLock(%s)", var);
    }
}
