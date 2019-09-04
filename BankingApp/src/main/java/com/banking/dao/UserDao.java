package com.banking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.banking.connection.ConnectionFactory;
import com.banking.user.User;

public class UserDao implements UserDaoInterface {

	private static Connection con;

	public User get(int id) {
		con = ConnectionFactory.getConnection();
		String sql = "SELECT * FROM user_table WHERE user_id = ?;";
		User u = null;
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int uId = rs.getInt("user_id");
				String uname = rs.getString("user_name");
				String pass = rs.getString("pass");
				u = new User(uId, uname, pass);
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ConnectionFactory.closeConnection(con);

		}
		ConnectionFactory.closeConnection(con);
		return u;
	}

	@Override
	public User getByUserName(String uname) {
		con = ConnectionFactory.getConnection();
		String sql = "SELECT * FROM user_table WHERE user_name = ?;";
		User u = null;
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, uname);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int uId = rs.getInt("user_id");
				String username = rs.getString("user_name");
				String pass = rs.getString("pass");
				u = new User(uId, username, pass);
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ConnectionFactory.closeConnection(con);

		}
		ConnectionFactory.closeConnection(con);
		return u;
	}

	public List<User> getAll() {
		con = ConnectionFactory.getConnection();
		List<User> userList = new ArrayList<User>();
		String sql = "Select * from user_table;";
		Statement statement;
		ResultSet rs;
		try {
			statement = con.createStatement();
			rs = statement.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("user_id");
				String uname = rs.getString("user_name");
				String pass = rs.getString("pass");

				User pull = new User(id, uname, pass);
				userList.add(pull);
			}
			statement.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ConnectionFactory.closeConnection(con);

		}

		ConnectionFactory.closeConnection(con);

		return userList;
	}

	public boolean save(User t) {
		if (null != t) {
			con = ConnectionFactory.getConnection();
			String uName = t.getUsername();
			String pass = t.getPassword();
			String sqlCheck = "SELECT user_name FROM user_table WHERE user_name =?;";
			try {
				PreparedStatement ps1 = con.prepareStatement(sqlCheck);
				ps1.setString(1, uName);
				ResultSet rs = ps1.executeQuery();
				if (rs.next()) {
					System.out.println("Username already exists");
					ps1.close();
					rs.close();
					ConnectionFactory.closeConnection(con);
					return false;
				} else {

					rs.close();
					String sql = "INSERT INTO user_table (user_name, pass) VALUES (?,?);";
					PreparedStatement ps = con.prepareStatement(sql);
					ps.setString(1, uName);
					ps.setString(2, pass);
					ps.executeUpdate();

					ps.close();
					ConnectionFactory.closeConnection(con);

					return true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				ConnectionFactory.closeConnection(con);
				return false;
			}
		} else {
			System.out.println("Null User");
			return false;
		}

	}

	public boolean update(User t, String[] params) {
		con = ConnectionFactory.getConnection();
		if (params.length != 2) {
			System.out.println("Incorrect number of parameters");
			ConnectionFactory.closeConnection(con);
			return false;
		} else {
			int id = t.getId();
			String uname = params[0];
			String pwd = params[1];
			String sql = "UPDATE user_table SET user_name = ?, pass = ? WHERE user_id = ?;";
			try {
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, uname);
				ps.setString(2, pwd);
				ps.setInt(3, id);
				ps.executeUpdate();
				ps.close();
				ConnectionFactory.closeConnection(con);
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ConnectionFactory.closeConnection(con);
				return false;
			}
		}
	}

	public boolean delete(User t) {
		if (null != t) {
			con = ConnectionFactory.getConnection();
			int id = t.getId();
			String sql = "DELETE FROM user_table WHERE user_id = ?;";
			String sql2 = "DELETE FROM account WHERE account_id like ?";
			try (PreparedStatement ps = con.prepareStatement(sql);
					PreparedStatement ps1 = con.prepareStatement(sql2);) {
				ps.setInt(1, id);
				ps.executeUpdate();
				ps1.setString(1, id + "-%");
				ps1.executeUpdate();
				ConnectionFactory.closeConnection(con);
				System.out.println("Successfully deleted account " + t.getUsername()+ "\n\n");
				return true;
			} catch (SQLException e) {
				e.printStackTrace();

				ConnectionFactory.closeConnection(con);
				return false;
			}
		} else {
			System.out.println("Null user");
			return false;
		}
	}

	@Override
	public List<String> getAllAccounts(int id) {
		con = ConnectionFactory.getConnection();
		List<String> accList = new ArrayList<String>();
		String sql = "SELECT * FROM UserAccount WHERE user_id = ?";
		try (PreparedStatement ps = con.prepareStatement(sql);) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				accList.add(rs.getString("account_id"));
			}
			rs.close();
			ConnectionFactory.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return accList;
	}

}
