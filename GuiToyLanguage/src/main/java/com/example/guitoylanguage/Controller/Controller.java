package com.example.guitoylanguage.Controller;

import com.example.guitoylanguage.Exceptions.MyException;
import com.example.guitoylanguage.Model.PrgState.PrgState;
import com.example.guitoylanguage.Model.Values.RefValue;
import com.example.guitoylanguage.Model.Values.Value;
import com.example.guitoylanguage.Repository.IRepository;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Pair {
    final PrgState first;
    final MyException second;

    public Pair(PrgState first, MyException second) {
        this.first = first;
        this.second = second;
    }
}

public class Controller {

    private ExecutorService executorService;
    IRepository repo;
    boolean displayFlag=true;
    public void setDisplayFlag(boolean value)
    {
        this.displayFlag=value;
    }
    public Controller(IRepository rep)
    {
        this.repo=rep;
    }


   public Map<Integer,Value> safeGarbageCollector(List<Integer> symTableAddr, List<Integer> heapAddr, Map<Integer, Value> heap)
    {
        return heap.entrySet().stream()
                .filter(e -> ( symTableAddr.contains(e.getKey()) || heapAddr.contains(e.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void conservativeGarbageCollector(List<PrgState> programStates) {
        List<Integer> symTableAddresses = Objects.requireNonNull(programStates.stream()
                        .map(p -> getAddrFromSymTable(p.getSymTable().values()))
                        .map(Collection::stream)
                        .reduce(Stream::concat).orElse(null))
                        .collect(Collectors.toList());
        programStates.forEach(p -> {
            p.getHeap().setContent((HashMap<Integer, Value>) safeGarbageCollector(symTableAddresses, getAddrFromHeap(p.getHeap().getContent().values()), p.getHeap().getContent()));
        });
    }

    public List<Integer> getAddrFromHeap(Collection<Value> heapValues) {
        return heapValues.stream()
                .filter(v->v instanceof RefValue)
                .map(v->{RefValue v1=(RefValue) v;return v1.getAddress();})
                .collect(Collectors.toList());
    }
    public List<Integer> getAddrFromSymTable(Collection<Value> symTableValues)
    {
        return symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();})
                .collect(Collectors.toList());
    }


    public void oneStepForAllPrograms(List<PrgState> programStates) throws MyException,IOException, InterruptedException {
        programStates.forEach(programState -> {
            try {
                repo.logPrgStateExec(programState);
                display(programState);
            } catch (IOException | MyException e) {
                System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
            }
        });
        List<Callable<PrgState>> callList = programStates.stream()
                .map((PrgState p) -> (Callable<PrgState>) (p::oneStep))
                .collect(Collectors.toList());

        List<Pair> newProgramList;
        newProgramList = executorService.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return new Pair(future.get(), null);
                    } catch (ExecutionException | InterruptedException e) {
                        if (e.getCause() instanceof MyException)
                            return new Pair(null, (MyException) e.getCause());
                        System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
                        return null;
                    }
                }).filter(Objects::nonNull)
                .filter(pair -> pair.first != null || pair.second != null)
                .collect(Collectors.toList());

        for (Pair error: newProgramList)
            if (error.second != null)
                throw error.second;
        programStates.addAll(newProgramList.stream().map(pair -> pair.first).collect(Collectors.toList()));

        programStates.forEach(programState -> {
            try {
                repo.logPrgStateExec(programState);
            } catch (IOException | MyException e) {
                System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
            }
        });
        repo.setProgramStates(programStates);
    }

    public void oneStep() throws MyException, InterruptedException,IOException {
        executorService = Executors.newFixedThreadPool(2);
        List<PrgState> programStates = removeCompletedPrg(repo.getProgramList());
        oneStepForAllPrograms(programStates);
        conservativeGarbageCollector(programStates);
        //programStates = removeCompletedPrg(repository.getProgramList());
        executorService.shutdownNow();
        //repository.setProgramStates(programStates);
    }


    public void allStep() throws InterruptedException,MyException, IOException {
        this.executorService = Executors.newFixedThreadPool(2);  // maximum 2 threads concurrently

        List<PrgState> programStates = removeCompletedPrg(this.repo.getProgramList());

        while (programStates.size() > 0) {

            conservativeGarbageCollector(programStates);
            oneStepForAllPrograms(programStates);

            programStates = removeCompletedPrg(repo.getProgramList());
        }
        executorService.shutdownNow();
        //repo.setProgramStates(programStates);
    }

    private void display(PrgState prg)
    {
        if(displayFlag)
            System.out.println(prg.toString());




    }

    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList)
    {
        return inPrgList.stream()
                .filter(p-> !p.isNotCompleted())
                .collect(Collectors.toList());

    }
    public void setProgramStates(List<PrgState> programStates) {
        this.repo.setProgramStates(programStates);
    }
    public List<PrgState> getProgramStates() {
        return this.repo.getProgramList();
    }

}
