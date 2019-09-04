package com.banking.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.banking.account.Account;
import com.banking.connection.ConnectionFactory;
import com.banking.user.User;

public class AccountDao implements AccountDaoInterface {

	private static Connection con;

	public Account get(String id) {
		con = ConnectionFactory.getConnection();
		String sql = "Select * FROM account WHERE account_id = ?";
		Account ac = null;
		if (id == null) {
			return ac;
		}
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String accId = rs.getString("account_id");
				String type = rs.getString("account_type");
				float accAmount = rs.getFloat("account_amount");
				ac = new Account(accId, type, accAmount);
			}
			rs.close();
			ConnectionFactory.closeConnection(con);
		} catch (SQLException e) {
			ConnectionFactory.closeConnection(con);
			e.printStackTrace();
		}
		return ac;
	}

	public List<Account> getAll() {
		con = ConnectionFactory.getConnection();
		String sql = "SELECT * FROM Account;";
		List<Account> accList = new ArrayList<Account>();
		try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql);) {
			while (rs.next()) {
				Account ac;
				String type = rs.getString("account_type");
				String id = rs.getString("account_id");
				float amount = rs.getFloat("account_amount");
				ac = new Account(type, id, amount);
				accList.add(ac);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		ConnectionFactory.closeConnection(con);
		return accList;
	}

	public boolean save(Account t, User u) {
		con = ConnectionFactory.getConnection();
		String sql = "INSERT INTO account VALUES (?,?,?);";
		String sql2 = "INSERT INTO UserAccount values(?,?);";
		if (t == null || u == null) {
			return false;
		}
		try (PreparedStatement ps = con.prepareStatement(sql); PreparedStatement ps2 = con.prepareStatement(sql2);) {
			ps.setString(1, t.getAccountNumber());
			ps.setString(2, t.getType());
			ps.setFloat(3, t.getAmount());
			ps.executeUpdate();

			ps2.setInt(1, u.getId());
			ps2.setString(2, t.getAccountNumber());
			ps2.executeUpdate();

			ConnectionFactory.closeConnection(con);
			return true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			ConnectionFactory.closeConnection(con);

			e.printStackTrace();
			return false;
		}

	}

	public boolean addMoney(String accId, float amount) {
		if (amount < 0) {
			System.out.println("Can not add negative value");
			return false;
		}
		con = ConnectionFactory.getConnection();
		String sqlCheck = "Select * from account where account_id = ?";
		String sql = "UPDATE account SET account_amount = account_amount + ? where account_id = ?";
		try (PreparedStatement psCheck = con.prepareStatement(sqlCheck);
				PreparedStatement ps = con.prepareStatement(sql)) {
			psCheck.setString(1, accId);
			ResultSet rs = psCheck.executeQuery();
			if (rs.next()) {
				ps.setFloat(1, amount);
				ps.setString(2, accId);
				ps.executeUpdate();
				ConnectionFactory.closeConnection(con);
				return true;
			} else {
				System.out.println("No such account exists");
				return false;
			}
		} catch (SQLException e) {
			ConnectionFactory.closeConnection(con);
			e.printStackTrace();
			return false;
		}
	}

	public boolean delete(Account t) {
		con = ConnectionFactory.getConnection();
		String sqlCheck = "Select * from account where account_id = ?";
		String sql = "DELETE FROM account WHERE account_id = ?";
		try (PreparedStatement psCheck = con.prepareStatement(sqlCheck);
				PreparedStatement ps = con.prepareStatement(sql);) {
			psCheck.setString(1, t.getAccountNumber());
			ResultSet rs = psCheck.executeQuery();
			if (rs.next()) {
				ps.setString(1, t.getAccountNumber());
				ps.executeUpdate();
				System.out.println("Sucessfully deleted account id: " + t.getAccountNumber() + "\n");
			} else {
				System.out.println("No such account exists");
				return false;
			}
			rs.close();
			ConnectionFactory.closeConnection(con);
			return true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ConnectionFactory.closeConnection(con);
			return false;
		}
	}

	@Override
	public boolean withdrawMoney(String accId, float amount) {
		if (amount < 0) {
			System.out.println("Can not withdraw negative value");
			return false;
		}
		con = ConnectionFactory.getConnection();
		String sqlCheck = "Select * from account where account_id = ?;";
		String sqlAmountCheck = "SELECT account_amount FROM account WHERE account_id = ?;";
		String sql = "UPDATE account SET account_amount = account_amount - ? where account_id = ?;";
		try (PreparedStatement psCheck = con.prepareStatement(sqlCheck);
				PreparedStatement psAmountCheck = con.prepareStatement(sqlAmountCheck);
				PreparedStatement ps = con.prepareStatement(sql)) {
			psCheck.setString(1, accId);
			ResultSet rs = psCheck.executeQuery();
			if (rs.next()) {
				float accAmount = 0;
				psAmountCheck.setString(1, accId);
				rs = psAmountCheck.executeQuery();
				if (rs.next()) {
					accAmount = rs.getFloat("account_amount");
				}
				if (amount > accAmount) {
					System.out.println("Can not pull out more than you have");
					rs.close();
					ConnectionFactory.closeConnection(con);
					return false;
				} else {
					ps.setFloat(1, amount);
					ps.setString(2, accId);
					ps.executeUpdate();

					rs.close();
					ConnectionFactory.closeConnection(con);
					return true;
				}
			} else {
				System.out.println("No such account exists");
				ConnectionFactory.closeConnection(con);

				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			ConnectionFactory.closeConnection(con);
			return false;
		}

	}

	@Override
	public boolean transferMoney(String acc1, String acc2, float amount) {
		con = ConnectionFactory.getConnection();
		String sql = "{call transfer_amount(?,?,?)}";
		try (CallableStatement cs = con.prepareCall(sql)) {
			cs.setString(1, acc1);
			cs.setString(2, acc2);
			cs.setFloat(3, amount);

			return cs.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(con);
		}
		return false;
	}

}
