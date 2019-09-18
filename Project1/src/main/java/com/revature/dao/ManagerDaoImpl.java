package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.connection.ConnectionFactory;
import com.revature.models.Manager;

public class ManagerDaoImpl implements ManagerDao {

	private static Connection con;

	public List<Manager> getAll() {
		con = ConnectionFactory.getConnection();
		String sql = "SELECT * FROM manager";
		List<Manager> managers = new ArrayList<>();
		try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				Manager m;
				int id = rs.getInt("mid");
				String dept = rs.getString("department");
				m = new Manager(id, dept);
				managers.add(m);
			}
			ConnectionFactory.closeConnection(con);
			return managers;
		} catch (SQLException e) {
			ConnectionFactory.closeConnection(con);
			e.printStackTrace();
			return null;
		}
	}

	public Manager getById(int id) {
		con = ConnectionFactory.getConnection();
		String sql = "SELECT * FROM manager WHERE mid = ?";
		Manager m = null;
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				m = new Manager(rs.getInt("mid"), rs.getString("department"));
			}
			rs.close();
			ConnectionFactory.closeConnection(con);
			return m;
		} catch (SQLException e) {
			ConnectionFactory.closeConnection(con);
			e.printStackTrace();
			return m;
		}
	}

	public int updateDept(Manager m, String dept) {
		con = ConnectionFactory.getConnection();
		int num = 0;
		String sql = "UPDATE manager SET department = ? WHERE mid = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, dept);
			ps.setInt(2, m.getmID());
			num = ps.executeUpdate();
			ConnectionFactory.closeConnection(con);
			return num;
		} catch (SQLException e) {
			ConnectionFactory.closeConnection(con);
			e.printStackTrace();
			return num;
		}
	}

	public int createManager(Manager m) {
		con = ConnectionFactory.getConnection();
		int num = 0;
		String sql = "INSERT INTO manager VALUES(?,?)";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, m.getmID());
			ps.setString(2, m.getDept());
			num = ps.executeUpdate();
			ConnectionFactory.closeConnection(con);
			return num;
		} catch (SQLException e) {
			ConnectionFactory.closeConnection(con);
			return num;
		}
	}

	public int deleteManager(Manager m) {
		con = ConnectionFactory.getConnection();
		int num = 0;
		String sql = "DELETE FROM manager WHERE mid = ?";
		try(PreparedStatement ps = con.prepareStatement(sql)){
			ps.setInt(1, m.getmID());
			num = ps.executeUpdate();
			ConnectionFactory.closeConnection(con);
			return num;
		} catch (SQLException e) {
			ConnectionFactory.closeConnection(con);
			e.printStackTrace();
			return num;
		}
	}

}
