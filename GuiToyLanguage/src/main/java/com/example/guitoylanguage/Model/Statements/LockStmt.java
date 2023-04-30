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

public class LockStmt implements IStmt{
    private final String var;
    private static final Lock lock = new ReentrantLock();

    public LockStmt(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyILockTable lockTable = state.getLockTable();
        if (symTable.isDefined(var)) {
            if (symTable.lookUp(var).getType().equals(new IntType())) {
                IntValue resultVar = (IntValue) symTable.lookUp(var);
                int foundIndex = resultVar.getValue();
                if (lockTable.containsKey(foundIndex)) {
                    if (lockTable.get(foundIndex) == -1) {
                        lockTable.update(foundIndex, state.getId());
                        state.setLockTable(lockTable);
                    } else {
                        state.getExeStack().push(this);
                    }
                } else {
                    throw new MyException("Index not found in lock table!");
                }
            } else {
                throw new MyException("Var does not match int type!");
            }
        } else {
            throw new MyException("Variable not defined!");
        }
        lock.unlock();
        return null;
    }

    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (typeEnv.lookUp(var).equals(new IntType())) {
            return typeEnv;
        } else {
            throw new MyException("Var does not match int type!");
        }
    }

    public IStmt deepCopy() {
        return new LockStmt(var);
    }

    public String toString() {
        return String.format("lock(%s)", var);
    }
}
