package com.project.pp.lin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.project.pp.utl.DbUtl;



public class GoodDao {
	public GoodDao() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public int checkGood(int memberNo, int id, String goodType) {
		String sql = "SELECT COUNT(member_no) FROM good WHERE member_no = ? AND record_id = ? AND record_type = ?;";
			
			Connection conn = null;
			PreparedStatement ps = null;
			//PreparedStatement ps1 = null;
			int goodChecked = 0;
			try {
				conn = DriverManager.getConnection(DbUtl.URL, DbUtl.USER, DbUtl.PASSWORD);
				ps = conn.prepareStatement(sql);
				ps.setInt(1, memberNo);
				ps.setInt(2, id);
				ps.setString(3, goodType);
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					goodChecked = rs.getInt(1);;
				}
				return goodChecked;
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					ps.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			System.out.println(goodChecked);
			return goodChecked;
			
	}
	
	public int insertGood(Good good) {
		int count = 0;
		String sql = "INSERT INTO good"
				+ "(record_type, record_id, member_no) "
				+ "VALUES(?, ?, ?);";
		String sql2 = "UPDATE activity_master SET collection_count =  collection_count + 1 WHERE activity_id = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		try {
			connection = DriverManager.getConnection(DbUtl.URL, DbUtl.USER, DbUtl.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps2 = connection.prepareStatement(sql2);
			ps.setString(1, good.getRecordType());
			ps.setInt(2, good.getRecordId());
			ps.setInt(3, good.getMemberNo());
			ps2.setInt(1, good.getRecordId());
			count = ps.executeUpdate();
			count = ps2.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}
	
	public int deleteGood(int memberNo, int id, String goodType) {
		int count = 0;
		String sql = "DELETE FROM good WHERE member_no = ? AND record_id = ? AND record_type = ?;";
		String sql2 ="UPDATE activity_master SET collection_count =  collection_count - 1 WHERE activity_id = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		try {
			connection = DriverManager.getConnection(DbUtl.URL, DbUtl.USER, DbUtl.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps2 = connection.prepareStatement(sql2);
			ps.setInt(1, memberNo);
			ps.setInt(2, id);
			ps.setString(3, goodType);
			ps2.setInt(1, id);
			count = ps.executeUpdate();
			count = ps2.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}
}
