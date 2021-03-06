package com.kharko.Utils;

import com.kharko.types.Worker;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.kharko.controllers.*;
import com.kharko.dao.EmployeeDataAcessor;
import com.kharko.dao.PersonDataAccessor;
import com.kharko.types.Position;
import com.kharko.types.Viddil;
import com.kharko.controllers.PositionController;

public class kek {
    public static EmployeeDataAcessor employeeDataAcessor;
    public static PersonDataAccessor dataAccessor;
    public static Stage primaryStage1;
    public static String driverClassName  = "dbClass";
    public static String URL = "jdbc:mysql://35.204.182.138:3306/Projects?useLegacyDatetimeCode=false&serverTimezone=UTC";
    public static String user = "root";
    public static String password = "123456";
    private static kek daun;

    public static kek init(Stage roflan){
        if(daun == null) {
            daun = new kek(roflan);
       }
        return daun;
    }

    public static kek getInstance(){
        return daun;
    }


    private kek() {
    }

    private kek(Stage roflan){
        primaryStage1 = roflan;
    }

    public void setConfig(String driverClassName1,String URL1,String user1, String password1) throws Exception{
        driverClassName = driverClassName1;
        URL = URL1;
        user = user1;
        password = password1;
        this.setPosad();
    }
    //jdbc:mysql://localhost:3306/projects
    public void setPosad() throws Exception{
        dataAccessor = new PersonDataAccessor(driverClassName, URL ,user,password);
        employeeDataAcessor = new EmployeeDataAcessor(driverClassName, URL ,user,password);
        ObservableList<Position> positions =  dataAccessor.getPersonList();
        ObservableList<Viddil> viddils = dataAccessor.getViddilList();
        ObservableList<Worker> employees = employeeDataAcessor.getPersonList();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/kharko/Position.fxml"));
        Parent root = loader.load();
        PositionController controller = loader.getController();

        controller.setData(positions);
        controller.setViddilDate(viddils);
        controller.setEmployees(employees);

        primaryStage1.setTitle("Posada");
        primaryStage1.setScene(new Scene(root, 700, 475));
        primaryStage1.show();
    }

    public void setLogin() throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/kharko/login.fxml"));
        Parent root = loader.load();
        primaryStage1 = new Stage();
        primaryStage1.setTitle("Hello World");
        primaryStage1.setScene(new Scene(root, 300, 400));
        primaryStage1.show();
    }

    public void setPay() throws Exception{
        employeeDataAcessor = new EmployeeDataAcessor(driverClassName, URL ,user,password);
        dataAccessor = new PersonDataAccessor(driverClassName, URL ,user,password);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/kharko/Pay.fxml"));
        Parent root = loader.load();
        PayController controller = loader.getController();
        controller.setWorkers(employeeDataAcessor.getPersonList());
        controller.setHeads(employeeDataAcessor.getHeadList());
        controller.setViddil(dataAccessor.getViddilList());
        primaryStage1.setTitle("Salary");
        primaryStage1.setScene(new Scene(root, 600, 300));
        primaryStage1.show();

    }

    public void setStatistics() throws Exception{
        employeeDataAcessor = new EmployeeDataAcessor(driverClassName, URL ,user,password);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/kharko/Statistics.fxml"));
        Parent root = loader.load();
        StatisticsController controller = loader.getController();

        controller.setHead(employeeDataAcessor.getHeadList());
        controller.setWorkers(employeeDataAcessor.getPersonList());

        primaryStage1.setTitle("Stats");
        primaryStage1.setScene(new Scene(root, 700, 400));
        primaryStage1.show();

    }
}
