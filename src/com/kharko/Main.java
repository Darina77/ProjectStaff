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
        kek.init(primaryStage).setLogin();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
