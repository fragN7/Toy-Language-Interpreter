package com.example.guitoylanguage.Model.utils;

import com.example.guitoylanguage.Exceptions.MyException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T>{
    Stack<T> stack;
    public MyStack(){
        this.stack=new Stack<>();}

    public T pop() throws MyException
    {
        if(stack.isEmpty())
            throw new MyException("Stack is empty");
        return this.stack.pop();
    }
    public void push(T elem)
    {
        this.stack.push(elem);
    }
    public String toString(){

        return this.stack.toString();
    }

    public T peek(){
        return this.stack.peek();
    }
    public boolean isEmpty(){
        return this.stack.isEmpty();
    }
    public int size(){
        return stack.size();
    }
    public List<T> getReversed(){
        List<T> list= Arrays.asList((T[]) stack.toArray());
        Collections.reverse(list);
        return list;
    }

}
