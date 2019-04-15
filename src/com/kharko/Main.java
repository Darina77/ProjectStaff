package com.kharko;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import com.kharko.Utils.kek;
import com.kharko.dao.PersonDataAccessor;

public class Main extends Application {
    public static PersonDataAccessor dataAccessor;
    private static Stage primaryStage1;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage1 = primaryStage;
        kek.init(primaryStage).setPosad();
        Platform.setImplicitExit(false);
    }

    public static void startOld() throws Exception{

        Stage newStage = new Stage();
        newStage.setTitle("Login");
        kek.getInstance().setPosad();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
