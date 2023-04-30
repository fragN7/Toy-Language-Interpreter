package com.example.guitoylanguage.Model.utils;

import com.example.guitoylanguage.Exceptions.MyException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyDictionary<T1,T2> implements MyIDictionary<T1,T2>{
    HashMap<T1,T2> dict;
    public MyDictionary(){this.dict=new HashMap<>();}
    public boolean isDefined(T1 key)
    {
        return this.dict.containsKey(key);
    }

    public T2 lookUp(T1 key) throws MyException
    {
        if(!isDefined(key))
            throw new MyException(key+" is not defined");
        return this.dict.get(key);
    }
    public void update(T1 key,T2 value) throws MyException
    {

        if(!isDefined(key))
            throw new MyException(key+" is not defined");
        this.dict.put(key,value);
    }
    public Collection<T2> values(){return this.dict.values();}

    public void remove(T1 key) throws MyException
    {
        if(!isDefined(key))
            throw new MyException(key+" is not defined");
        this.dict.remove(key);
    }

    @Override
    public MyIDictionary<T1, T2> deepCopy() throws MyException {
        MyIDictionary<T1, T2> toReturn = new MyDictionary<>();
        for (T1 key: keySet())
            toReturn.put(key, lookUp(key));
        return toReturn;
    }

    public Map<T1, T2> getContent()
    {
        return dict;
    }
    public Set<T1> keySet(){
        return this.dict.keySet();
    }

    public String toString(){return this.dict.toString();}
    public void put(T1 key,T2 value){this.dict.put(key,value);}
}
