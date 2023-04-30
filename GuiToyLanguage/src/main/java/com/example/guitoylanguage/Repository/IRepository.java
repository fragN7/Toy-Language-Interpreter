package com.example.guitoylanguage.Repository;

import com.example.guitoylanguage.Exceptions.MyException;
import com.example.guitoylanguage.Model.PrgState.PrgState;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    List<PrgState> getProgramList();
    void setProgramStates(List<PrgState> programStates);
    PrgState getCurrentState();
    void addProgram(PrgState program);
    void logPrgStateExec(PrgState p) throws IOException,MyException;
    void emptyLogFile() throws IOException,MyException;
}
