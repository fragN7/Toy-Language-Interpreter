package com.example.guitoylanguage.Model.Expressions;

import com.example.guitoylanguage.Exceptions.MyException;
import com.example.guitoylanguage.Model.Types.BoolType;
import com.example.guitoylanguage.Model.Types.Type;
import com.example.guitoylanguage.Model.Values.BoolValue;
import com.example.guitoylanguage.Model.Values.Value;
import com.example.guitoylanguage.Model.utils.MyIDictionary;
import com.example.guitoylanguage.Model.utils.MyIHeap;

import java.util.Objects;

public class LogicExp implements Exp{
    Exp exp1;
    Exp exp2;
    String op;
    public LogicExp(String op1,Exp e1,Exp e2)
    {
        this.exp1=e1;
        this.exp2=e2;
        this.op=op1;
    }
    public Value eval(MyIDictionary<String,Value> symTable, MyIHeap heap) throws MyException {
        Value value1,value2;
        value1=this.exp1.eval(symTable,heap);
        if(value1.getType().equals(new BoolType()))
        {
            value2=this.exp2.eval(symTable,heap);
            if(value2.getType().equals(new BoolType()))
            {
                BoolValue bool1=(BoolValue) value1;
                BoolValue bool2=(BoolValue) value2;
                boolean b1,b2;
                b1=bool1.getValue();
                b2=bool2.getValue();
                if(Objects.equals(this.op,"and"))
                {
                    return new BoolValue(b1 && b2);
                }
                else if (Objects.equals(this.op,"or"))
                {
                    return new BoolValue(b1 || b2);
                }

            }
            else {
                throw new MyException("Second operand is not bool");
            }
        }
        else {
            throw new MyException("First operand is not bool");
        }
        return null;
    }
    public Exp deepCopy(){

        return new LogicExp(op,exp1.deepCopy(),exp2.deepCopy());
    }
    public String toString(){
        return exp1.toString()+" "+op.toString()+" "+exp2.toString();}
    public Type typeCheck(MyIDictionary<String,Type> typeEnv) throws MyException
    {
        Type typ1,typ2;
        typ1=exp1.typeCheck(typeEnv);
        typ2=exp2.typeCheck(typeEnv);
        if(typ1.equals(new BoolType()))
        {
            if(typ2.equals(new BoolType()))
                return new BoolType();
            else throw new MyException("second operand not bool");
        }
        else throw new MyException("first operand not bool");

    }
}
