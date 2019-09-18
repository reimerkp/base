package com.revature.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.connection.ConnectionFactory;
import com.revature.models.Image;

public class ImgDaoImpl implements ImgDao {

	private static Connection con;
	private int imgId;

	@Override
	public int InsertImgFile(int id, InputStream f, String name) {
		con = ConnectionFactory.getConnection();
		int num = 0;
		try (PreparedStatement ps = con.prepareStatement("INSERT INTO profimg VALUES (?,?,?)")) {
			imgId = 1;
			String imgD = "" + id + "" + imgId;
			imgId = Integer.parseInt(imgD);
			System.out.println(imgId);
			ps.setInt(1, imgId);
			ps.setString(2, name);
			ps.setBinaryStream(3, f);
			num = ps.executeUpdate();

			ConnectionFactory.closeConnection(con);
			return imgId;
		} catch (SQLException e1) {
			num = -3;
			e1.printStackTrace();
			ConnectionFactory.closeConnection(con);
			return num;
		}
	}

	public Image getImageById(int id) {
		Image i = null;
		con = ConnectionFactory.getConnection();
		try (PreparedStatement ps = con.prepareStatement("SELECT * FROM profimg WHERE imgid = ?")) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int imgId = rs.getInt("imgid");
				byte[] data = rs.getBytes("img");
				String imgName = rs.getString("imgtext");
				i = new Image(imgId, imgName, data);
			}
			rs.close();
			ConnectionFactory.closeConnection(con);
			return i;
		} catch (SQLException e) {
			e.printStackTrace();
			ConnectionFactory.closeConnection(con);
			return i;
		}
	}

	@Override
	public byte[] getImgBytes(int id) {
		System.out.println("In getImgBytes");
		con = ConnectionFactory.getConnection();
		byte[] imgBytes = null;
		try (PreparedStatement ps = con.prepareStatement("SELECT img FROM profimg WHERE imgid = ?")) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					imgBytes = rs.getBytes(1);
				}
			}
			rs.close();
			ConnectionFactory.closeConnection(con);
			return imgBytes;
		} catch (SQLException e) {
			ConnectionFactory.closeConnection(con);
			return imgBytes;
		}
	}

	@Override
	public int updateImg(int id, InputStream f, String fName) {
		con = ConnectionFactory.getConnection();
		int num;
		String sql = "UPDATE profimg SET imgtext = ?, img = ? WHERE imgid = ?";
		try (PreparedStatement ps = con.prepareStatement(sql);) {
			ps.setString(1, fName);
			ps.setBinaryStream(2, f);
			ps.setInt(3, id);
			num = ps.executeUpdate();
			ConnectionFactory.closeConnection(con);
			return num;
		} catch (SQLException e) {
			num = -1;
			ConnectionFactory.closeConnection(con);
			return num;
		}
	}

	@Override
	public int deleteImg(int id) {
		con = ConnectionFactory.getConnection();
		int num = -1;
		String sql = "DELETE FROM profimg WHERE imgid = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, id);
			num = ps.executeUpdate();
			ConnectionFactory.closeConnection(con);
			return num;
		} catch (SQLException e) {
			ConnectionFactory.closeConnection(con);
			return num;
		}
	}

}
