package com.example.guitoylanguage.Model.Statements;


import com.example.guitoylanguage.Exceptions.MyException;
import com.example.guitoylanguage.Model.Expressions.Exp;
import com.example.guitoylanguage.Model.Expressions.RelationalExpr;
import com.example.guitoylanguage.Model.Expressions.VarExp;
import com.example.guitoylanguage.Model.PrgState.PrgState;
import com.example.guitoylanguage.Model.Types.Type;
import com.example.guitoylanguage.Model.Types.IntType;
import com.example.guitoylanguage.Model.utils.MyIDictionary;
import com.example.guitoylanguage.Model.utils.MyIStack;

public class ForStmt implements IStmt{
    private final String variable;
    private final Exp expression1;
    private final Exp expression2;
    private final Exp expression3;
    private final IStmt statement;

    public ForStmt(String variable, Exp expression1, Exp expression2, Exp expression3, IStmt statement) {
        this.variable = variable;
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.expression3 = expression3;
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> exeStack = state.getExeStack();
        IStmt convertToWhile = new CompStmt(new AssignStmt("v", expression1),
                                   new WhileStmt(new RelationalExpr("<", new VarExp("v"), expression2),
                                       new CompStmt(statement, new AssignStmt("v", expression3))));
        exeStack.push(convertToWhile);
        state.setExeStack(exeStack);
        return null;
    }

    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type1 = expression1.typeCheck(typeEnv);
        Type type2 = expression2.typeCheck(typeEnv);
        Type type3 = expression3.typeCheck(typeEnv);

        if (type1.equals(new IntType()) && type2.equals(new IntType()) && type3.equals(new IntType()))
            return typeEnv;
        else
            throw new MyException("Invalid for statement!");
    }

    public IStmt deepCopy() {
        return new ForStmt(variable, expression1.deepCopy(), expression2.deepCopy(), expression3.deepCopy(), statement.deepCopy());
    }

    public String toString() {
        return String.format("for(%s=%s; %s<%s; %s=%s) {%s}", variable, expression1, variable, expression2, variable, expression3, statement);
    }
}
