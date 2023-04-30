package com.example.guitoylanguage.Model.Expressions;

import com.example.guitoylanguage.Exceptions.MyException;
import com.example.guitoylanguage.Model.Types.RefType;
import com.example.guitoylanguage.Model.Types.Type;
import com.example.guitoylanguage.Model.Values.RefValue;
import com.example.guitoylanguage.Model.Values.Value;
import com.example.guitoylanguage.Model.utils.MyIDictionary;
import com.example.guitoylanguage.Model.utils.MyIHeap;



public class HeapReading implements Exp{
    private  final Exp expression;

    public HeapReading(Exp e)
    {
        this.expression=e;
    }

    public Value eval(MyIDictionary<String,Value> symTable, MyIHeap heap) throws MyException
    {
        Value val=expression.eval(symTable,heap);
        if(!(val instanceof RefValue))
            throw new MyException(String.format("%s not of RefType",val));
        RefValue refValue=(RefValue) val;
        return heap.get(refValue.getAddress());

    }

    public Exp deepCopy()
    {
        return new HeapReading(expression.deepCopy());
    }

    public String toString()
    {
        return String.format("ReadHeap(%s)",expression);
    }

    public Type typeCheck(MyIDictionary<String,Type> typeEnv) throws MyException
    {
        Type typ=expression.typeCheck(typeEnv);
        if(typ instanceof RefType)
        {
            RefType reft=(RefType) typ;
            return reft.getInner();
        } else throw new MyException("The RH Argument is not a RefType");


    }


}
