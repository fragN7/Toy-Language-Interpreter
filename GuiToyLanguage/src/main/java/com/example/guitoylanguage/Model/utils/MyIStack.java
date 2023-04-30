package com.example.guitoylanguage.Model.utils;

import com.example.guitoylanguage.Exceptions.MyException;

import java.util.List;

public interface MyIStack<T>{
    T pop() throws MyException;
    void push(T element);
    T peek();
    boolean isEmpty();
    List<T> getReversed();
    int size();
}
