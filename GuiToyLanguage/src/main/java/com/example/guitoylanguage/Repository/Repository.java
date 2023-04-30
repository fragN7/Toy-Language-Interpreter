package com.example.guitoylanguage.Repository;

import com.example.guitoylanguage.Exceptions.MyException;
import com.example.guitoylanguage.Model.PrgState.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{
    private List<PrgState> programStates;
    private int currPos;
    private final String logFilePath;

    public Repository(PrgState prgs,String lgfp) throws IOException,MyException
    {
        this.logFilePath=lgfp;
        this.programStates=new ArrayList<>();
        this.currPos=0;
        this.addProgram(prgs);
        this.emptyLogFile();

    }
    public int getCurrPos(){return this.currPos;}

    public void setCurrPos(int currentPos)
    {
        this.currPos=currentPos;
    }

    @Override
    public List<PrgState> getProgramList()
    {
        return this.programStates;
    }

    //setPrgList
    @Override
    public void setProgramStates(List<PrgState> programState)
    {
        this.programStates=programState;
    }

    public void addProgram(PrgState progr)
    {
        this.programStates.add(progr);

    }

    @Override
    public void logPrgStateExec(PrgState p) throws IOException,MyException
    {
        PrintWriter logFile;
        logFile=new PrintWriter(new BufferedWriter(new FileWriter(logFilePath,true)));
        logFile.println(p.programStateToString());
        logFile.close();

    }

    @Override
    public void emptyLogFile() throws IOException,MyException
    {
        PrintWriter logFile;
        logFile= new PrintWriter(new BufferedWriter(new FileWriter(logFilePath,false)));
        logFile.close();
    }

    @Override
    public PrgState getCurrentState()
    {
        return this.programStates.get(this.currPos);
    }



}
