package com.example.guitoylanguage.Model.PrgState;

import com.example.guitoylanguage.Exceptions.MyException;
import com.example.guitoylanguage.Model.Statements.IStmt;
import com.example.guitoylanguage.Model.Values.Value;
import com.example.guitoylanguage.Model.utils.*;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class PrgState {
    private MyIList<Value> out;
    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String,Value> symTable;
    private MyIHeap heap;

    private MyILockTable lockTable;

    private int id;

    private  static int lastID=0;
    private MyIDictionary<String, BufferedReader> fileTable;

    public PrgState(MyIStack<IStmt> stack, MyIDictionary<String,Value> symTable, MyIList<Value> out, MyIDictionary<String, BufferedReader> fileTable, MyIHeap heap,MyILockTable lockTable, IStmt program) {
        this.exeStack = stack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.lockTable = lockTable;
        this.heap = heap;
        IStmt originalProgram = program.deepCopy();
        this.exeStack.push(originalProgram);
        this.id=setID();

    }

    public PrgState(MyIStack<IStmt> stack, MyIDictionary<String,Value> symTable, MyIList<Value> out, MyIDictionary<String, BufferedReader> fileTable, MyIHeap heap, MyILockTable lockTable) {
        this.exeStack = stack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.lockTable = lockTable;
        this.id=setID();

    }

    public synchronized int setID()
    {
        lastID++;
        return lastID;
    }

    public void sID()
    {
        id=setID();
    }
    public int getId() {
        return this.id;
    }


    public PrgState oneStep()
            throws MyException, IOException
    {
        if(exeStack.isEmpty())
            throw new MyException("PrgState stack is empty");
        IStmt crtStmt=exeStack.pop();
        return crtStmt.execute(this);
    }

    public boolean isNotCompleted() {
        return exeStack.isEmpty();
    }

    public void setExeStack(MyIStack<IStmt> newStack) {
        this.exeStack = newStack;
    }

    public void setSymTable(MyIDictionary<String, Value> newSymTable) {
        this.symTable = newSymTable;
    }

    public void setOut(MyIList<Value> newOut) {
        this.out = newOut;
    }

    public void setFileTable(MyIDictionary<String, BufferedReader> newFileTable) {
        this.fileTable = newFileTable;
    }

    public void setHeap(MyIHeap newHeap) {
        this.heap = newHeap;
    }

    public void setLockTable(MyILockTable newLockTable) {
        this.lockTable = newLockTable;
    }
    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }

    public int getExeStackSize()
    {
        return exeStack.size();
    }

    public MyIDictionary<String, Value> getSymTable() {
        return symTable;
    }

    public MyIList<Value> getOut() {
        return out;
    }

    public MyIDictionary<String, BufferedReader> getFileTable() {
        return fileTable;
    }

    public MyIHeap getHeap() {
        return heap;
    }

    public MyILockTable getLockTable(){
        return this.lockTable;
    }
    public String exeStackToString() {
        StringBuilder exeStackStringBuilder = new StringBuilder();
        List<IStmt> stack = exeStack.getReversed();
        for (IStmt statement: stack) {
            exeStackStringBuilder.append(statement.toString()).append("\n");
        }
        return exeStackStringBuilder.toString();
    }

    public String symTableToString() throws MyException {
        StringBuilder symTableStringBuilder = new StringBuilder();
        for (String key: symTable.keySet()) {
            symTableStringBuilder.append(String.format("%s -> %s\n", key, symTable.lookUp(key).toString()));
        }
        return symTableStringBuilder.toString();
    }

    public String outToString() {
        StringBuilder outStringBuilder = new StringBuilder();
        for (Value elem: out.getList()) {
            outStringBuilder.append(String.format("%s\n", elem.toString()));
        }
        return outStringBuilder.toString();
    }

    public String fileTableToString() {
        StringBuilder fileTableStringBuilder = new StringBuilder();
        for (String key: fileTable.keySet()) {
            fileTableStringBuilder.append(String.format("%s\n", key));
        }
        return fileTableStringBuilder.toString();
    }

    public String heapToString() throws MyException {
        StringBuilder heapStringBuilder = new StringBuilder();
        for (int key: heap.keySet()) {
            heapStringBuilder.append(String.format("%d -> %s\n", key, heap.get(key)));
        }
        return heapStringBuilder.toString();
    }

    public String lockTableToString() throws MyException {
        StringBuilder lockTableStringBuilder = new StringBuilder();
        for (int key: lockTable.keySet()) {
            lockTableStringBuilder.append(String.format("%d -> %d\n", key, lockTable.get(key)));
        }
        return lockTableStringBuilder.toString();
    }

    @Override
    public String toString() {
        return "ID: "+ id+ "\nExecution stack: \n" + exeStack.getReversed() + "\nSymbol table: \n" + symTable.toString() + "\nOutput list: \n" + out.toString()+"\n"+"FileTable: "+fileTable.toString()+"\n"+"Heap:"+heap.toString()+ "\n" + "Lock Table:" + lockTable.toString() + "\n";
    }

    public String programStateToString() throws MyException {
        return "ID:" +id+ "\nExecution stack: \n" + exeStackToString() + "Symbol table: \n" + symTableToString() + "Output list: \n" + outToString()+"FileTable: " +fileTableToString()+"\n"+ "Heap:" +heapToString()+ "\n" +"Lock Table:" + lockTableToString() + "\n";
    }
}

