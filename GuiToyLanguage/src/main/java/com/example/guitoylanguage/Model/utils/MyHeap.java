package com.example.guitoylanguage.Model.utils;

import com.example.guitoylanguage.Exceptions.MyException;
import com.example.guitoylanguage.Model.Values.Value;

import java.util.HashMap;
import java.util.Set;

public class MyHeap implements MyIHeap{
    HashMap<Integer, Value> heap;
    Integer freeLocationVal;
    public int newValue(){
        freeLocationVal+=1;
        while(freeLocationVal==0 || heap.containsKey(freeLocationVal))
            freeLocationVal+=1;
        return freeLocationVal;
    }
    public MyHeap()
    {
        this.heap=new HashMap<>();
        freeLocationVal=1;
    }
    public int getFreeValue()
    {
        return freeLocationVal;
    }

    public HashMap<Integer,Value> getContent()
    {
        return heap;
    }

    public void setContent(HashMap<Integer,Value> newMap)
    {
        this.heap=newMap;
    }

    public int add(Value value)
    {
        heap.put(freeLocationVal,value);
        Integer toReturn=freeLocationVal;
        freeLocationVal=newValue();
        return toReturn;
    }

    public void update(Integer pos,Value val) throws MyException
    {
        if(!heap.containsKey(pos))
            throw new MyException(String.format("%d is not present in the heap",pos));
        heap.put(pos,val);
    }
    public Value get(Integer pos) throws MyException
    {
        if(!heap.containsKey(pos))
            throw new MyException(String.format("%d is not present in the heap",pos));
        return heap.get(pos);
    }
    public boolean containsKey(Integer pos)
    {
        return this.heap.containsKey(pos);
    }

    public void remove(Integer key) throws MyException
    {
        if(!containsKey(key))
            throw new MyException(key +" is not defined");
        freeLocationVal=key;
        this.heap.remove(key);
    }
    public Set<Integer> keySet()
    {
        return heap.keySet();
    }

    public String toString()
    {
        return this.heap.toString();
    }
}
