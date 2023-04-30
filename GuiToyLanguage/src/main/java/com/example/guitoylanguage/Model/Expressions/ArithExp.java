package com.example.guitoylanguage.Model.Expressions;

import com.example.guitoylanguage.Exceptions.MyException;
import com.example.guitoylanguage.Model.Types.IntType;
import com.example.guitoylanguage.Model.Types.Type;
import com.example.guitoylanguage.Model.Values.IntValue;
import com.example.guitoylanguage.Model.Values.Value;
import com.example.guitoylanguage.Model.utils.MyIDictionary;
import com.example.guitoylanguage.Model.utils.MyIHeap;

public class ArithExp implements Exp{
    Exp exp1;
    Exp exp2;
    char op;
    public ArithExp(char o,Exp e1,Exp e2)
    {
        this.op=o;
        this.exp2=e2;
        this.exp1=e1;
    }
    public Value eval(MyIDictionary<String, Value> symTable, MyIHeap heap) throws MyException {
        Value val1,val2;
        val1=this.exp1.eval(symTable,heap);
        if(val1.getType().equals(new IntType()))
        {
            val2=this.exp2.eval(symTable,heap);
            if(val2.getType().equals(new IntType()))
            {
                IntValue int1=(IntValue) val1;
                IntValue int2=(IntValue) val2;
                int n1,n2;
                n1=int1.getValue();
                n2=int2.getValue();
                if(this.op=='+')
                    return new IntValue(n1+n2);
                else if(this.op=='-')
                    return new IntValue(n1-n2);
                else if(this.op=='*')
                    return new IntValue(n1*n2);
                else if(this.op=='/')
                    if(n2==0)
                        throw new MyException("Division by zero not allowed");
                    else
                        return new IntValue(n1/n2);
            }
            else
                throw new MyException("Second operand is not int");
        }
        else
            throw new MyException("First operand is not int");

        return null;
    }
    public Exp deepCopy(){

        return new ArithExp(op,exp1.deepCopy(),exp2.deepCopy());
    }
    public String toString()
    {

        return exp1.toString()+" "+op+" "+exp2.toString();
    }
    public Type typeCheck(MyIDictionary<String,Type> typeEnv) throws MyException
    {
        Type typ1,typ2;
        typ1=exp1.typeCheck(typeEnv);
        typ2=exp2.typeCheck(typeEnv);
        if(typ1.equals(new IntType()))
        {
            if(typ2.equals(new IntType()))
                return new IntType();
            else throw new MyException("second operand not integer");
        }
        else throw new MyException("first operand not integer");

    }

    }

