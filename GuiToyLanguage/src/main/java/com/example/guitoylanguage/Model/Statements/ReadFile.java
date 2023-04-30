package com.example.guitoylanguage.Model.Statements;

import com.example.guitoylanguage.Exceptions.MyException;
import com.example.guitoylanguage.Model.Expressions.Exp;
import com.example.guitoylanguage.Model.PrgState.PrgState;
import com.example.guitoylanguage.Model.Types.IntType;
import com.example.guitoylanguage.Model.Types.StringType;
import com.example.guitoylanguage.Model.Types.Type;
import com.example.guitoylanguage.Model.Values.IntValue;
import com.example.guitoylanguage.Model.Values.StringValue;
import com.example.guitoylanguage.Model.Values.Value;
import com.example.guitoylanguage.Model.utils.MyIDictionary;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStmt {

    private final Exp expression;
    private final String varName;

    public ReadFile(Exp express, String varN) {
        this.varName = varN;
        this.expression = express;

    }

    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();

        if (symTable.isDefined(varName)) {
            Value value = symTable.lookUp(varName);
            if (value.getType().equals(new IntType())) {
                value = expression.eval(symTable, state.getHeap());
                if (value.getType().equals(new StringType())) {
                    StringValue castValue = (StringValue) value;
                    if (fileTable.isDefined(castValue.getValue())) {
                        BufferedReader br = fileTable.lookUp(castValue.getValue());
                        try {
                            String line = br.readLine();
                            if (line == null)
                                line = "0";
                            symTable.put(varName, new IntValue(Integer.parseInt(line)));

                        } catch (IOException e) {
                            throw new MyException(String.format("Could not read from file %s", castValue));
                        }

                    } else
                        throw new MyException(String.format("The file table does not contain %s", castValue));
                } else
                    throw new MyException(String.format("s does not evaluate to StringType", value));


            } else
                throw new MyException(String.format("%s is not of type IntType", value));

        } else
            throw new MyException(String.format("%s is not present in the SymTable", varName));

        return null;
    }

    public IStmt deepCopy() {
        return new ReadFile(expression.deepCopy(), varName);
    }

    public String toString()
    {
        return String.format("ReadFile(%s,%s)",expression.toString(),varName);
    }

    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String,Type> typeEnv) throws MyException
    {
        if(expression.typeCheck(typeEnv).equals(new StringType()))
            if(typeEnv.lookUp(varName).equals(new IntType()))
                return typeEnv;
            else throw new MyException("ReadFile needs an integer  as var parameter");
        else throw new MyException("ReadFile needs string as expression parameter");
    }

}
