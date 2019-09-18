package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.connection.ConnectionFactory;
import com.revature.models.ReinbursementRequest;

public class ReinbursementDaoImpl implements ReinbursementDao {

	private static Connection con;

	public List<ReinbursementRequest> getAll() {
		con = ConnectionFactory.getConnection();
		List<ReinbursementRequest> requests = new ArrayList<>();
		String sql = "SELECT * FROM reinbursement_request";
		try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql);) {
			while (rs.next()) {
				ReinbursementRequest reinbursement;
				int rid = rs.getInt("rid");
				String reason = rs.getString("reason");
				double amount = rs.getDouble("amount");
				int eid = rs.getInt("eid");
				Integer mid = (Integer) rs.getObject("mid");
				Boolean approved = (Boolean) rs.getObject("approved");
				if (mid != null && approved != null)
					reinbursement = new ReinbursementRequest(rid, reason, amount, eid, mid, approved);
				else
					reinbursement = new ReinbursementRequest(rid, reason, amount, eid);
				requests.add(reinbursement);
			}
			ConnectionFactory.closeConnection(con);
			return requests;
		} catch (SQLException e) {
			ConnectionFactory.closeConnection(con);
			e.printStackTrace();
			return null;
		}
	}

	public ReinbursementRequest getById(int id) {
		con = ConnectionFactory.getConnection();
		ReinbursementRequest reinbursement = null;
		String sql = "SELECT * FROM reinbursement_request WHERE rid = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int rid = rs.getInt("rid");
				String reason = rs.getString("reason");
				double amount = rs.getDouble("amount");
				int eid = rs.getInt("eid");
				Integer mid = (Integer) rs.getObject("mid");
				Boolean approved = (Boolean) rs.getObject("approved");
				if (mid != null && approved != null)
					reinbursement = new ReinbursementRequest(rid, reason, amount, eid, mid, approved);
				else
					reinbursement = new ReinbursementRequest(rid, reason, amount, eid);
			}
			rs.close();
			ConnectionFactory.closeConnection(con);
			return reinbursement;
		} catch (SQLException e) {
			ConnectionFactory.closeConnection(con);
			e.printStackTrace();
			return reinbursement;
		}
	}

	public List<ReinbursementRequest> getAllByEid(int eID) {
		con = ConnectionFactory.getConnection();
		List<ReinbursementRequest> requests = new ArrayList<>();
		String sql = "SELECT * FROM reinbursement_request WHERE eid = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, eID);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ReinbursementRequest reinbursement;
				int rid = rs.getInt("rid");
				String reason = rs.getString("reason");
				double amount = rs.getDouble("amount");
				int eid = rs.getInt("eid");
				Integer mid = (Integer) rs.getObject("mid");
				Boolean approved = (Boolean) rs.getObject("approved");
				if (mid != null && approved != null)
					reinbursement = new ReinbursementRequest(rid, reason, amount, eid, mid, approved);
				else
					reinbursement = new ReinbursementRequest(rid, reason, amount, eid);
				requests.add(reinbursement);

			}
			rs.close();
			ConnectionFactory.closeConnection(con);
			return requests;
		} catch (SQLException e) {
			ConnectionFactory.closeConnection(con);
			e.printStackTrace();
			return requests;
		}

	}

	public int setManagerApproval(int rId, int mID, boolean approved) {
		con = ConnectionFactory.getConnection();
		int num = 0;
		String sql = "UPDATE reinbursement_request SET mid = ?, approved = ? WHERE rid = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, mID);
			ps.setBoolean(2, approved);
			ps.setInt(3, rId);
			num = ps.executeUpdate();
			ConnectionFactory.closeConnection(con);
			return num;
		} catch (SQLException e) {
			ConnectionFactory.closeConnection(con);
			e.printStackTrace();
			return num;
		}
	}

	public int createReinbursementRequest(ReinbursementRequest r) {
		con = ConnectionFactory.getConnection();
		int num = 0;
		String sql = "INSERT INTO reinbursement_request(reason,amount,eid) VALUES (?,?,?)";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, r.getReason());
			ps.setDouble(2, r.getAmount());
			ps.setInt(3, r.geteID());
			num = ps.executeUpdate();
			ConnectionFactory.closeConnection(con);
			return num;
		} catch (SQLException e) {
			e.printStackTrace();
			ConnectionFactory.closeConnection(con);
			return num;
		}
	}

	public int updateReinbursementRequest(ReinbursementRequest old, ReinbursementRequest newR) {
		con = ConnectionFactory.getConnection();
		int num = 0;
		String sql = "UPDATE reinbursement_request SET reason = ?, amount = ? WHERE rid = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, newR.getReason());
			ps.setDouble(2, newR.getAmount());
			ps.setInt(3, old.getrID());
			num = ps.executeUpdate();
			ConnectionFactory.closeConnection(con);
			return num;
		} catch (SQLException e) {
			e.printStackTrace();
			ConnectionFactory.closeConnection(con);
			return num;
		}
	}

	public int deleteReinbursementRequest(ReinbursementRequest r) {
		con = ConnectionFactory.getConnection();
		int num = 0;
		String sql = "DELETE FROM reinbursement_request WHERE rid = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, r.getrID());
			num = ps.executeUpdate();
			ConnectionFactory.closeConnection(con);
			return num;
		} catch (SQLException e) {
			e.printStackTrace();
			ConnectionFactory.closeConnection(con);
			return num;
		}
	}

}
