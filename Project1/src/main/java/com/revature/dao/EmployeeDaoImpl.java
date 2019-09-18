package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.connection.ConnectionFactory;
import com.revature.models.Employee;
import com.revature.services.ImageService;

public class EmployeeDaoImpl implements EmployeeDao {

	private static Connection con;

	public List<Employee> getAll() {
		con = ConnectionFactory.getConnection();
		String sql = "SELECT * FROM employee ORDER BY eid";
		List<Employee> returnList = new ArrayList<>();
		try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql);) {
			while (rs.next()) {
				Employee e;
				int id = rs.getInt("eid");
				String fName = rs.getString("fname");
				String lName = rs.getString("lname");
				String email = rs.getString("email");
				String uName = rs.getString("uname");
				String pwd = rs.getString("pass");
				int imgId = rs.getInt("imgid");
				e = new Employee(id, fName, lName, email, uName, pwd, imgId);
				returnList.add(e);
			}

		} catch (SQLException e) {
			ConnectionFactory.closeConnection(con);
			e.printStackTrace();
		}
		ConnectionFactory.closeConnection(con);
		return returnList;
	}

	public List<String> getAllUsernames() {
		con = ConnectionFactory.getConnection();
		String sql = "SELECT uname from employee";
		List<String> unames = new ArrayList<>();
		try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				String userName = rs.getString("uname");
				unames.add(userName);
			}
		} catch (SQLException e) {
			ConnectionFactory.closeConnection(con);
			e.printStackTrace();
		}
		ConnectionFactory.closeConnection(con);
		return unames;
	}

	public Employee getByUsernameAndPassword(String uname, String pwd) {
		con = ConnectionFactory.getConnection();
		String sql = "SELECT * FROM employee WHERE uname = ? AND pass = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			Employee e = null;
			ps.setString(1, uname);
			ps.setString(2, pwd);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("eid");
				String fname = rs.getString("fname");
				String lname = rs.getString("lname");
				String email = rs.getString("email");
				String pass = rs.getString("pass");
				int imgId = rs.getInt("imgid");
				e = new Employee(id, fname, lname, email, uname, pass, imgId);

			}
			rs.close();
			ConnectionFactory.closeConnection(con);
			return e;
		} catch (SQLException e) {
			ConnectionFactory.closeConnection(con);
			e.printStackTrace();
			return null;
		}
	}

	public Employee getById(int id) {
		con = ConnectionFactory.getConnection();
		String sql = "SELECT * FROM employee WHERE eid = ?";
		Employee e = null;
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String fname = rs.getString("fname");
				String lname = rs.getString("lname");
				String email = rs.getString("email");
				String uname = rs.getString("uname");
				String pass = rs.getString("pass");
				int imgId = rs.getInt("imgid");
				e = new Employee(id, fname, lname, email, uname, pass, imgId);

			}
			rs.close();
			ConnectionFactory.closeConnection(con);
			return e;
		} catch (SQLException ex) {
			ConnectionFactory.closeConnection(con);
			ex.printStackTrace();
			return null;
		}
	}

	public Employee authEmpl(int id) {
		con = ConnectionFactory.getConnection();
		String sql = "SELECT * FROM employee WHERE eid = ?";
		Employee e = null;
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String uname = rs.getString("uname");
				e = new Employee(id, uname);
			}
			rs.close();
			ConnectionFactory.closeConnection(con);
			return e;
		} catch (SQLException e1) {
			ConnectionFactory.closeConnection(con);
			e1.printStackTrace();
			return e;
		}
	}

	public int createEmployee(Employee e) {
		con = ConnectionFactory.getConnection();
		String sql = "INSERT INTO employee(fname, lname, email, uname, pass) VALUES(?,?,?,?,?);";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, e.getfName());
			ps.setString(2, e.getlName());
			ps.setString(3, e.getEmail());
			ps.setString(4, e.getUname());
			ps.setString(5, e.getPwd());
			int num = ps.executeUpdate();
			ConnectionFactory.closeConnection(con);
			return num;
		} catch (SQLException e1) {
			ConnectionFactory.closeConnection(con);
			return -1;
		}
	}

	public int updateEmployee(int oldID, Employee newE) {
		con = ConnectionFactory.getConnection();
		String sql = "UPDATE employee SET fname = ?, lname = ?, email = ?, pass = ?, imgId = ? WHERE eid = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, newE.getfName());
			ps.setString(2, newE.getlName());
			ps.setString(3, newE.getEmail());
			ps.setString(4, newE.getPwd());
			ps.setInt(5, newE.getImgId());
			ps.setInt(6, oldID);
			int num = ps.executeUpdate();
			ConnectionFactory.closeConnection(con);
			return num;
		} catch (SQLException e) {
			e.printStackTrace();
			ConnectionFactory.closeConnection(con);
			return -1;
		}
	}

	public int deleteEmployee(Employee e) {
		con = ConnectionFactory.getConnection();
		String sql = "DELETE FROM employee WHERE eID = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, e.geteID());
			int num = ps.executeUpdate();
			ConnectionFactory.closeConnection(con);
			return num;
		} catch (SQLException e1) {
			ConnectionFactory.closeConnection(con);
			e1.printStackTrace();
			return 0;
		}
	}

}
