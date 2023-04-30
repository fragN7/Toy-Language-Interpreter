package com.example.guitoylanguage.Model.Statements;

import com.example.guitoylanguage.Exceptions.MyException;
import com.example.guitoylanguage.Model.Expressions.Exp;
import com.example.guitoylanguage.Model.PrgState.PrgState;
import com.example.guitoylanguage.Model.Types.StringType;
import com.example.guitoylanguage.Model.Types.Type;
import com.example.guitoylanguage.Model.Values.StringValue;
import com.example.guitoylanguage.Model.Values.Value;
import com.example.guitoylanguage.Model.utils.MyIDictionary;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseReadFile implements IStmt{
    private final Exp expression;

    public CloseReadFile(Exp express)
    {
        this.expression=express;
    }

    public PrgState execute(PrgState state)throws MyException
    {
        Value value=expression.eval(state.getSymTable(),state.getHeap());
        if(!value.getType().equals(new StringType()))
            throw new MyException(String.format("%s does not evaluate to StringValue",expression));
        StringValue fileName=(StringValue) value;
        MyIDictionary<String, BufferedReader> fileTable=state.getFileTable();
        if(!fileTable.isDefined(fileName.getValue()))
            throw new MyException(String.format("%s is not present in FileTable",value));
        BufferedReader br=fileTable.lookUp(fileName.getValue());
        try{
            br.close();

        }
        catch (IOException e)
        {
            throw new MyException(String.format("Unexpected error in closing %s",value));

        }
        fileTable.remove(fileName.getValue());
        state.setFileTable(fileTable);
        return null;
    }

    public IStmt deepCopy()
    {
        return new CloseReadFile(expression.deepCopy());
    }

    public String toString()
    {
        return String.format("CloseReadFile(%s)",expression.toString());
    }


    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String,Type> typeEnv) throws MyException
    {
        if(expression.typeCheck(typeEnv).equals(new StringType()))
            return typeEnv;
        else throw new MyException("CloseReadFile requires a string expression");
    }
}
