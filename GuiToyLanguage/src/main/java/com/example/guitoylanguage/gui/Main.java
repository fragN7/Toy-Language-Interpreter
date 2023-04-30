package com.example.guitoylanguage.gui;

import com.example.guitoylanguage.Exceptions.MyException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception, IOException, MyException {
        FXMLLoader programListLoader = new FXMLLoader();
        programListLoader.setLocation(Main.class.getResource("/com/example/guitoylanguage/ProgramChooserController.fxml"));
        Parent programListRoot = programListLoader.load();
        Scene programListScene = new Scene(programListRoot, 500, 550);
        programListScene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource("/com/example/guitoylanguage/button_style.css")).toExternalForm());
       ProgramChooserController programChooserController = programListLoader.getController();
        primaryStage.setTitle("Select a program");
        primaryStage.setScene(programListScene);
        primaryStage.show();

        FXMLLoader programExecutorLoader = new FXMLLoader();
        programExecutorLoader.setLocation(Main.class.getResource("/com/example/guitoylanguage/ProgramExecutorController.fxml"));
        Parent programExecutorRoot = programExecutorLoader.load();
        Scene programExecutorScene = new Scene(programExecutorRoot, 700, 500);
        programExecutorScene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource("/com/example/guitoylanguage/button_style.css")).toExternalForm());
        ProgramExecutorController programExecutorController = programExecutorLoader.getController();
        programChooserController.setProgramExecutorController(programExecutorController);
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Interpreter");
        secondaryStage.setScene(programExecutorScene);
        secondaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
