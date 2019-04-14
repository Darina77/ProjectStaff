package com.rozhko.Controller;

import java.sql.*;
import java.util.*;

import com.rozhko.Models.Data;
import com.rozhko.Models.Item;

public class DbAccess {
	private Connection conn = null;
	private String user = "root";
	private String passwd = "123456";

	public DbAccess() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception ex) {
			System.out.println("DbAccess> " + ex.getMessage());
		}
	}

	public boolean connectionDb(String db) {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://35.204.182.138:3306/" + db + "?useSSL=false", user, passwd);
			return true;
		} catch (SQLException e) {
			System.out.println("connectionDB> " + e.getMessage());
			return false;
		}
	}

	public void disConnect() {
		try {
			conn.close();
			conn = null;
		} catch (SQLException e) {
			System.out.println("disConnect> " + e.getMessage());
		}
	}

	public Vector<Item> getProjects() {
		Vector<Item> list = new Vector<>();
		String sql = "select id, nameProject from Projects";
		try {
			Statement statement = conn.createStatement();
			statement.execute(sql);
			ResultSet rs = statement.getResultSet();
			while ((rs != null) && (rs.next())) {
				list.addElement(new Item(rs.getString(1), rs.getString(2)));
			}
		} catch (SQLException e) {
			System.out.println("getProjects : " + e.getMessage());
		}
		return list;
	}

	public Vector<Item> getStagesByProjectId(String idProject) {
		Vector<Item> list = new Vector<>();
		String sql = "select idStage, numStage from Stages where idProject='" + idProject + "'";
		try {
			Statement statement = conn.createStatement();
			statement.execute(sql);
			ResultSet rs = statement.getResultSet();
			while ((rs != null) && (rs.next())) {
				list.addElement(new Item(rs.getString(1), rs.getString(2)));
			}
		} catch (SQLException e) {
			System.out.println("getStagesByProjectId : " + e.getMessage());
		}
		return list;
	}

	public Vector<Item> getEmpoyees() {
		Vector<Item> list = new Vector<>();
		String sql = "select idEmp, surname from Employees";
		try {
			Statement statement = conn.createStatement();
			statement.execute(sql);
			ResultSet rs = statement.getResultSet();
			while ((rs != null) && (rs.next())) {
				list.addElement(new Item(rs.getString(1), rs.getString(2)));
			}
		} catch (SQLException e) {
			System.out.println("getStagesByProjectId : " + e.getMessage());
		}
		return list;
	}

	public int createTask(String empId, String startDate, String endDate, String desc, String reward, String stage) {
		Random rand = new Random();
		int idWork = 0;
		long idTransaction = 0;
		try {
			PreparedStatement ps = conn.prepareStatement(
					"" + "INSERT INTO `Projects`.`Works` (`startDate`, `endDate`, `descriptionWork`, `idEmp`, `idStage`) "
							+ "VALUES (?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, startDate);
			ps.setString(2, endDate);
			ps.setString(3, desc);
			ps.setString(4, empId);
			ps.setString(5, stage);

			ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()){
				idWork = rs.getInt(1);
			}
			
			PreparedStatement transaction = conn.prepareStatement(
					"INSERT INTO accounting_transaction (`amount`, `description`) values (?, ?);", Statement.RETURN_GENERATED_KEYS
					);
			transaction.setString(1, reward);
			transaction.setString(2, "reward for work");
			
			transaction.executeUpdate();
			
			ResultSet rs2 = transaction.getGeneratedKeys();
			if (rs2.next()){
				idTransaction = rs2.getInt(1);
				System.out.println(idTransaction);
			}
			

			PreparedStatement rwd = conn
					.prepareStatement("INSERT INTO `Projects`.`accounting_payment_to_employee` (`stage_id`, `transaction_id`, `employee_work_id`) VALUES (?, ?, ?);");
			rwd.setString(1, stage);
			rwd.setLong(2, idTransaction);
			rwd.setInt(3, idWork);

			rwd.executeUpdate();

		} catch (SQLException e) {
			System.out.println("addPerson :" + e.getMessage());
		}
		return idWork;

	}

	public Vector<Data> getTable(String name, String id, String idStage) {
		Vector<Data> list = new Vector<>();
		StringBuilder sql = new StringBuilder(
				"SELECT Employees.surname AS name, Projects.nameProject, s.numStage, Works.startDate, Works.endDate, accounting_transaction.amount as price\n"
						+ "FROM (((((Works INNER JOIN Employees ON Works.idEmp = Employees.idEmp)\n"
						+ "INNER JOIN accounting_payment_to_employee ON Works.idWork = accounting_payment_to_employee.employee_work_id)\n"
						+ "INNER JOIN accounting_transaction ON accounting_payment_to_employee.transaction_id = accounting_transaction.id)\n"
						+ "INNER JOIN Stages as s ON Works.idStage = s.idStage)\n"
						+ "INNER JOIN Projects ON s.idProject = Projects.id\n)");
		if (name.length() > 0 || id.length() > 0 || idStage.length() > 0) {
			sql.append(" WHERE ");

			if (name.length() > 0) {
				sql.append("(LOWER(Employees.surname) LIKE LOWER('%" + name + "%'))");
				if (id.length() > 0) {
					sql.append(" AND ");
				}
			}
			if (id.length() > 0) {
				sql.append("Projects.id = " + id);
			}
			if (idStage.length() > 0) {
				sql.append(" AND s.idStage = " + idStage);
			}
			
			System.out.println(sql);
		}
		try {
			Statement statement = conn.createStatement();
			statement.execute(sql.toString());
			ResultSet rs = statement.getResultSet();
			while ((rs != null) && (rs.next())) {
				Data row = new Data(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6));
				list.addElement(row);
			}
		} catch (SQLException e) {
			System.out.println("getTable : " + e.getMessage());
		}
		return list;
	}

}
