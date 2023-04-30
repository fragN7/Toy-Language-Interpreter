package com.example.guitoylanguage.Model.utils;

import com.example.guitoylanguage.Exceptions.MyException;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements MyIList<T>{
    List<T> list;
    public MyList(){
        this.list=new ArrayList<>();
    }
    @Override
    public void add(T elem)
    {
        this.list.add(elem);
    }
    public T pop() throws MyException
    {
        if(list.isEmpty())
            throw new MyException("The list is empty");
        return this.list.remove(0);
    }
    public boolean isEmpty(){
        return this.list.isEmpty();
    }
    public List<T> getList(){
        return list;
    }
    public String toString(){
        return this.list.toString();
    }



}
