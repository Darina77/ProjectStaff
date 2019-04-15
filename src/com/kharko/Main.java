package com.kharko;

import javafx.application.Application;
import javafx.stage.Stage;
import com.kharko.Utils.kek;
import com.kharko.dao.PersonDataAccessor;

public class Main extends Application {
    public static PersonDataAccessor dataAccessor;
    private static Stage primaryStage1;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage1 = primaryStage;
        kek.init(primaryStage).setLogin();
    }

    public void startOld() throws Exception{
        Stage newStage = new Stage();
        newStage.setTitle("Login");
        kek.init(newStage).setLogin();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
