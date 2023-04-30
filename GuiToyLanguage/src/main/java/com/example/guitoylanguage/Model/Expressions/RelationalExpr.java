package com.example.guitoylanguage.Model.Expressions;

import com.example.guitoylanguage.Exceptions.MyException;
import com.example.guitoylanguage.Model.Types.BoolType;
import com.example.guitoylanguage.Model.Types.IntType;
import com.example.guitoylanguage.Model.Types.Type;
import com.example.guitoylanguage.Model.Values.BoolValue;
import com.example.guitoylanguage.Model.Values.IntValue;
import com.example.guitoylanguage.Model.Values.Value;
import com.example.guitoylanguage.Model.utils.MyIDictionary;
import com.example.guitoylanguage.Model.utils.MyIHeap;

import java.util.Objects;

public class RelationalExpr implements Exp{

    Exp expr1;
    Exp expr2;
    String operator;

    public RelationalExpr(String op,Exp e1,Exp e2)
    {
        this.expr1=e1;
        this.expr2=e2;
        this.operator=op;
    }

    @Override
    public Value eval(MyIDictionary<String,Value> symTable, MyIHeap heap) throws MyException
    {
        Value val1,val2;
        val1=this.expr1.eval(symTable,heap);
        if(val1.getType().equals(new IntType()))
        {
            val2=this.expr2.eval(symTable,heap);
            if(val2.getType().equals(new IntType()))
            {
                IntValue vall1=(IntValue) val1;
                IntValue vall2=(IntValue) val2;
                int v1,v2;
                v1=vall1.getValue();
                v2=vall2.getValue();
                if(Objects.equals(this.operator,"<"))
                    return new BoolValue(v1<v2);
                else if (Objects.equals(this.operator,"<="))
                    return new BoolValue(v1<=v2);
                else if (Objects.equals(this.operator,"=="))
                    return new BoolValue(v1==v2);
                else if (Objects.equals(this.operator,"!="))
                    return new BoolValue(v1!=v2);
                else if (Objects.equals(this.operator,">"))
                    return new BoolValue(v1>v2);
                else if (Objects.equals(this.operator,">="))
                    return new BoolValue(v1>=v2);


            }
            else {
                throw new MyException("Second operand is not integer.");
            }

        }
        else
        {
            throw new MyException("First operand is not an integer.");

        }
        return null;
    }

    public Exp deepCopy()
    {
        return new RelationalExpr(operator,expr1.deepCopy(),expr2.deepCopy());

    }

    public String toString()
    {

        return this.expr1.toString()+" "+this.operator+" "+this.expr2.toString();
    }

    public Type typeCheck(MyIDictionary<String,Type> typeEnv) throws MyException
    {
        Type typ1,typ2;
        typ1=expr1.typeCheck(typeEnv);
        typ2=expr2.typeCheck(typeEnv);
        if(typ1.equals(new IntType()))
        {
            if(typ2.equals(new IntType()))
                return new BoolType();
            else throw new MyException("second operand not integer");
        }
        else throw new MyException("first operand not integer");

    }

}

