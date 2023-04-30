package com.example.guitoylanguage.Model.utils;

import com.example.guitoylanguage.Exceptions.MyException;
import com.example.guitoylanguage.Model.Values.Value;

import java.util.HashMap;
import java.util.Set;

public interface MyIHeap {
    int getFreeValue();
    HashMap<Integer, Value> getContent();
    void setContent(HashMap<Integer,Value> newMap);
    int add(Value value);
    void update(Integer pos,Value val) throws MyException;
    Value get(Integer pos ) throws MyException;
    boolean containsKey(Integer pos);
    void remove(Integer key) throws MyException;
    Set<Integer> keySet();
}
