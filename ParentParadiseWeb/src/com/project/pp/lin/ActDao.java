package com.project.pp.lin;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.project.pp.utl.DbUtl;

public class ActDao {
	public ActDao() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public List<Act> getAll() {
//		String sql = "SELECT activity_id, member_no, activity_title, activity_type, city_code,"
//				+ "dist_code, activity_discribe, good_count, collection_count FROM activity_master;";
		String sql = "SELECT * FROM activity_master";
//		String sql1 = "SELECT city_name FROM city WHERE city_code = ?";
//		
//		String sql2 = "SELECT * FROM act city WHERE city_code = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		//PreparedStatement ps1 = null;
		try {
			conn = DriverManager.getConnection(DbUtl.URL, DbUtl.USER, DbUtl.PASSWORD);
			ps = conn.prepareStatement(sql);
			//ps1 = conn.prepareStatement(sql2);
			ResultSet rs = ps.executeQuery();
			List<Act> actList = new ArrayList<Act>();
			while (rs.next()) {
				int activityId = rs.getInt("activity_id");
				int memberNo = rs.getInt("member_no");
				String activityTitle = rs.getString("activity_title");
				int activityType = rs.getInt("activity_type");
				String cityCode = rs.getString("city_code");
				
//				ResultSet rs1 = ps.executeQuery(rs.getString(5));
//				rs1.next();
//				String cityCode = rs1.getString(1);
				
				String distCode = rs.getString("dist_code");
				String activityDescribe = rs.getString("activity_discribe");
				int goodCount = rs.getInt("good_count");
				int collectionCount = rs.getInt("collection_count");
				//long publishDate = rs.getTimestamp(5).getTime();
				Act act = new Act(activityId, memberNo, activityTitle, activityType, cityCode, distCode,
						activityDescribe, goodCount, collectionCount);
				actList.add(act);
			}
			return actList;
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
		return null;
	}
	
	public List<Act> getSort(int actType) {

		String sql = "SELECT * FROM activity_master WHERE activity_type = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		//PreparedStatement ps1 = null;
		try {
			conn = DriverManager.getConnection(DbUtl.URL, DbUtl.USER, DbUtl.PASSWORD);
			ps = conn.prepareStatement(sql);
			ps.setInt(1, actType);
			ResultSet rs = ps.executeQuery();
			List<Act> actList = new ArrayList<Act>();
			while (rs.next()) {
				int activityId = rs.getInt("activity_id");
				int memberNo = rs.getInt("member_no");
				String activityTitle = rs.getString("activity_title");
				int activityType = rs.getInt("activity_type");
				String cityCode = rs.getString("city_code");
				String distCode = rs.getString("dist_code");
				String activityDescribe = rs.getString("activity_discribe");
				int goodCount = rs.getInt("good_count");
				int collectionCount = rs.getInt("collection_count");
				//long publishDate = rs.getTimestamp(5).getTime();
				Act act = new Act(activityId, memberNo, activityTitle, activityType, cityCode, distCode,
						activityDescribe, goodCount, collectionCount);
				actList.add(act);
			}
			return actList;
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
		return null;
	}
	
	public List<Act> getHistory(int memberNumber) {

		String sql = "SELECT * FROM activity_master WHERE activity_id IN "
				+ "(SELECT activity_id FROM activity_register WHERE member_no = ?);";
		
		Connection conn = null;
		PreparedStatement ps = null;
		//PreparedStatement ps1 = null;
		try {
			conn = DriverManager.getConnection(DbUtl.URL, DbUtl.USER, DbUtl.PASSWORD);
			ps = conn.prepareStatement(sql);
			ps.setInt(1, memberNumber);
			ResultSet rs = ps.executeQuery();
			List<Act> actList = new ArrayList<Act>();
			while (rs.next()) {
				int activityId = rs.getInt("activity_id");
				int memberNo = rs.getInt("member_no");
				String activityTitle = rs.getString("activity_title");
				int activityType = rs.getInt("activity_type");
				String cityCode = rs.getString("city_code");
				String distCode = rs.getString("dist_code");
				String activityDescribe = rs.getString("activity_discribe");
				int goodCount = rs.getInt("good_count");
				int collectionCount = rs.getInt("collection_count");
				//long publishDate = rs.getTimestamp(5).getTime();
				Act act = new Act(activityId, memberNo, activityTitle, activityType, cityCode, distCode,
						activityDescribe, goodCount, collectionCount);
				actList.add(act);
			}
			return actList;
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
		return null;
	}
	
	public List<Act> getWatch(int memberNumber, String recordType) {

		String sql = "SELECT * FROM activity_master WHERE activity_id IN "
				+ "(SELECT record_id FROM good WHERE member_no = ? AND record_type = ?);";
		
		Connection conn = null;
		PreparedStatement ps = null;
		//PreparedStatement ps1 = null;
		try {
			conn = DriverManager.getConnection(DbUtl.URL, DbUtl.USER, DbUtl.PASSWORD);
			ps = conn.prepareStatement(sql);
			ps.setInt(1, memberNumber);
			ps.setString(2, recordType);
			ResultSet rs = ps.executeQuery();
			List<Act> actList = new ArrayList<Act>();
			while (rs.next()) {
				int activityId = rs.getInt("activity_id");
				int memberNo = rs.getInt("member_no");
				String activityTitle = rs.getString("activity_title");
				int activityType = rs.getInt("activity_type");
				String cityCode = rs.getString("city_code");
				String distCode = rs.getString("dist_code");
				String activityDescribe = rs.getString("activity_discribe");
				int goodCount = rs.getInt("good_count");
				int collectionCount = rs.getInt("collection_count");
				//long publishDate = rs.getTimestamp(5).getTime();
				Act act = new Act(activityId, memberNo, activityTitle, activityType, cityCode, distCode,
						activityDescribe, goodCount, collectionCount);
				actList.add(act);
			}
			return actList;
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
		return null;
	}
	
	public byte[] getImage(int id) {
		String sql = "SELECT activity_photo FROM activity_master WHERE activity_id = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		byte[] image = null;
		try {
			connection = DriverManager.getConnection(DbUtl.URL, DbUtl.USER,
					DbUtl.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				image = rs.getBytes(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					// When a Statement object is closed,
					// its current ResultSet object is also closed
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return image;
	}
	
}
