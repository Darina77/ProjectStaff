package com.projects.Controller;

import com.daryna.Controller.DbAccess;
import com.projects.Models.Project;
import com.projects.Models.Stages;
import com.rozhko.Models.Item;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class StagesController {

    private static String tableName = "Stages";
    private DbAccess access;

    public StagesController(DbAccess access)
    {
        this.access = access;
    }

    public List<Stages> getAllStages()
    {
        access.connectionDb();
        String sql = "select * from " + tableName;
        List<Stages> stages = new ArrayList<>();
        ResultSet res = access.getSet(sql);
        try {
            if (res != null)
            {
                while (res.next()){
                    stages.add(new Stages(res.getInt(1), res.getInt(2), res.getString(3), res.getString(4),
                            res.getString(5), res.getDouble(6), res.getDouble(7), res.getInt(8)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        access.disConnect();
        return stages;
    }


    public Vector<Item> getAllProjects()
    {
        access.connectionDb();
        String sql = "select * from Projects";
        Vector<Item> projects = new Vector<>();
        ResultSet res = access.getSet(sql);
        try {
            if (res != null)
            {
                while (res.next()){
                    projects.add(new Item( res.getString(1), res.getString(2)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        access.disConnect();
        return projects;
    }

    public boolean addStage(Stages newStage)
    {
        access.connectionDb();
        String sql = "insert into " + tableName + " (`numStage`, `startDate`, `endDatePlan`, `endDateReal`, `price`, `cost`, `idProject`) " +
                "values ('" + newStage.getNumStage() + "', '" + newStage.getStartDate() + "', '"+ newStage.getPlanEndDate()
                + "', '"+ newStage.getRealEndDate() + "', '"+ newStage.getPrice() +"', '"+ newStage.getCost() +"', '"+ newStage.getIdProject() +"')";
        boolean res = access.add(sql);
        access.disConnect();
        return res;
    }

    public boolean removeStage(int stageId)
    {
        access.connectionDb();
        String sql = "delete from " + tableName + " where ( `idStage` = '" + stageId + "')";
        boolean res = access.update(sql);
        access.disConnect();
        return res;
    }

    public boolean updateStage(List<Stages> all)
    {
        access.connectionDb();
        boolean res = true;
        for (Stages stage: all)
        {
           String sql = "update " + tableName + " set `numStage` = '"+ stage.getNumStage() + "', `startDate` = '"+ stage.getStartDate()
                   + "', `endDatePlan` = '" + stage.getPlanEndDate() +"', `endDateReal` = '" + stage.getRealEndDate() +
                   "', `price` = '" + stage.getPrice() +"', `cost` = '" + stage.getCost() + "', `idProject` = '"+ stage.getIdProject() +
                   "' where (`idStage` ='"+ stage.getIdStage() + "')";
            res = access.update(sql);
            if(!res) break;
        }
        access.disConnect();
        return res;
    }
}
