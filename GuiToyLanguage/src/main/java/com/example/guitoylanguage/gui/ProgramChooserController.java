package com.example.guitoylanguage.gui;

import com.example.guitoylanguage.Controller.Controller;
import com.example.guitoylanguage.Exceptions.MyException;
import com.example.guitoylanguage.Model.Expressions.*;
import com.example.guitoylanguage.Model.PrgState.PrgState;
import com.example.guitoylanguage.Model.Statements.*;
import com.example.guitoylanguage.Model.Statements.IStmt;
import com.example.guitoylanguage.Model.Types.IntType;
import com.example.guitoylanguage.Model.Types.RefType;
import com.example.guitoylanguage.Model.Values.IntValue;
import com.example.guitoylanguage.Model.utils.*;
import com.example.guitoylanguage.Repository.IRepository;
import com.example.guitoylanguage.Repository.Repository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProgramChooserController {
    private ProgramExecutorController programExecutorController;

    public void setProgramExecutorController(ProgramExecutorController programExecutorController) {
        this.programExecutorController = programExecutorController;
    }
    @FXML
    private ListView<IStmt> programsListView;

    @FXML
    private Button displayButton;

    @FXML
    public void initialize() {
        programsListView.setItems(getAllStatements());
        programsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    private void displayProgram(ActionEvent actionEvent) {
        IStmt selectedStatement = programsListView.getSelectionModel().getSelectedItem();
        if (selectedStatement == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error encountered!");
            alert.setContentText("No statement selected!");
            alert.showAndWait();
        } else {
            int id = programsListView.getSelectionModel().getSelectedIndex();
            try {
                selectedStatement.typeCheck(new MyDictionary<>());
                PrgState programState = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), new MyLockTable(), selectedStatement);
                IRepository repository = new Repository(programState, "log" + (id + 1) + ".txt");
                Controller controller = new Controller(repository);
                programExecutorController.setController(controller);
            } catch (MyException | IOException exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error encountered!");
                alert.setContentText(exception.getMessage());
                alert.showAndWait();
            }
        }
    }

    @FXML
    private ObservableList<IStmt> getAllStatements() {
        List<IStmt> allStatements = new ArrayList<>();

        IStmt ex1 = new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                            new CompStmt(new NewStmt("a", new ValueExp(new IntValue(20))),
                                new CompStmt(new VarDeclStmt("v", new IntType()),
                                        new CompStmt(new ForStmt("v", new ValueExp(new IntValue(0)), new ValueExp(new IntValue(3)), new ArithExp('+', new VarExp("v"), new ValueExp(new IntValue(1))),
                                                new ForkStmt(new CompStmt(new PrintStmt(new VarExp("v")),
                                                        new AssignStmt("v", new ArithExp('*', new VarExp("v"), new HeapReading(new VarExp("a"))))))),
                                                            new PrintStmt(new HeapReading(new VarExp("a")))))));
        allStatements.add(ex1);

        IStmt ex2 = new CompStmt(new VarDeclStmt("v1", new RefType(new IntType())),
                        new CompStmt(new VarDeclStmt("v2", new RefType(new IntType())),
                            new CompStmt(new VarDeclStmt("x", new IntType()),
                                new CompStmt(new VarDeclStmt("q", new IntType()),
                                    new CompStmt(new NewStmt("v1", new ValueExp(new IntValue(20))),
                                        new CompStmt(new NewStmt("v2", new ValueExp(new IntValue(30))),
                                            new CompStmt(new NewLockStmt("x"),
                                                new CompStmt(new ForkStmt(
                                                    new CompStmt(new ForkStmt(
                                                        new CompStmt(new LockStmt("x"),
                                                            new CompStmt(new HeapWriting("v1", new ArithExp('-', new HeapReading(new VarExp("v1")), new ValueExp(new IntValue(1)))), new UnlockStmt("x")))),
                                                                new CompStmt(new LockStmt("x"),
                                                                    new CompStmt(new HeapWriting("v1", new ArithExp('*', new HeapReading(new VarExp("v1")), new ValueExp(new IntValue(10)))), new UnlockStmt("x"))))),
                                                                        new CompStmt( new NewLockStmt("q"),
                                                                                new CompStmt(new ForkStmt(
                                                                                        new CompStmt( new ForkStmt(
                                                                                                new CompStmt(new LockStmt("q"),
                                                                                                        new CompStmt(new HeapWriting("v2", new ArithExp('+', new HeapReading(new VarExp("v2")), new ValueExp(new IntValue(5)))), new UnlockStmt("q")))),
                                                                                                            new CompStmt(new LockStmt("q"),
                                                                                                                new CompStmt(new HeapWriting("v2", new ArithExp('*', new HeapReading(new VarExp("v2")), new ValueExp(new IntValue(10)))), new UnlockStmt("q"))))),
                                                                                                                    new CompStmt(new NopStmt(),
                                                                                                                        new CompStmt(new NopStmt(),
                                                                                                                            new CompStmt(new NopStmt(),
                                                                                                                                new CompStmt(new NopStmt(),
                                                                                                                                    new CompStmt(new LockStmt("x"),
                                                                                                                                        new CompStmt(new PrintStmt(new HeapReading(new VarExp("v1"))),
                                                                                                                                            new CompStmt(new UnlockStmt("x"),
                                                                                                                                                new CompStmt(new LockStmt("q"),
                                                                                                                                                    new CompStmt(new PrintStmt(new HeapReading(new VarExp("v2"))),
                                                                                                                                                        new UnlockStmt("q"))))))))))))))))))));
        allStatements.add(ex2);

        return FXCollections.observableArrayList(allStatements);
    }
}
