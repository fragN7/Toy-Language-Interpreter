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
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFile implements IStmt {
    private final Exp expression;

    public OpenRFile(Exp expression) {
        this.expression = expression;
    }

    public PrgState execute(PrgState state) throws MyException {
        Value value = expression.eval(state.getSymTable(), state.getHeap());
        if (value.getType().equals(new StringType())) {
            StringValue fileName = (StringValue) value;
            MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
            if (!fileTable.isDefined(fileName.getValue())) {
                BufferedReader br;
                try {
                    br = new BufferedReader(new FileReader(fileName.getValue()));

                } catch (FileNotFoundException e) {
                    throw new MyException(String.format("%s could not be opened.", fileName.getValue()));

                }
                fileTable.put(fileName.getValue(), br);
                state.setFileTable(fileTable);
            }
        }
        return null;
    }

    public IStmt deepCopy() {
        return new OpenRFile(expression.deepCopy());
    }

    public String toString()
    {
        return String.format("OpenRFile(%s)",expression.toString());
    }

    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String,Type> typeEnv) throws MyException
    {
        if(expression.typeCheck(typeEnv).equals(new StringType()))
            return typeEnv;
        else
            throw new MyException("OpenReadFile requires string expression");
    }


}
