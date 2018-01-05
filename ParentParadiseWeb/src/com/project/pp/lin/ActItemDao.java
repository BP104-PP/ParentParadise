package com.project.pp.lin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import com.project.pp.utl.DbUtl;

//import sun.misc.BASE64Encoder;

public class ActItemDao {
	public ActItemDao() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public ActItem getItem(int id) {
		String sql = "SELECT item_no, (SELECT activity_photo From activity_master WHERE activity_id = ?) as act_photo\n" + 
				", (SELECT activity_title From activity_master WHERE activity_id = ?) as act_title\n" + 
				", activity_fee, item_desc, (SELECT collection_count FROM activity_master WHERE activity_id = ?) as collection_count\n" + 
				", (SELECT good_count FROM activity_master WHERE activity_id = ?) as good_count\n" + 
				", (SELECT member_no FROM activity_master WHERE activity_id = ?) as member_no\n" +
				", (SELECT mb_photo FROM member WHERE member_no = (SELECT member_no FROM activity_master WHERE activity_id = ?)) as member_photo\n" +
				", (SELECT first_name FROM member WHERE member_no = (SELECT member_no FROM activity_master WHERE activity_id = ?)) as member_name\n" + 
				", (SELECT COUNT(member_no) FROM activity_master WHERE member_no =  (SELECT member_no FROM activity_master WHERE activity_id = ?)) as memberActCount\n" + 
				", (SELECT activity_discribe FROM activity_master WHERE activity_id = ?) as discribe\n" + 
				", act_item_desc, reg_startdate, reg_duedate, activity_startdate, activity_count, place_desc \n" + 
				", (SELECT city_name From city WHERE city_code = place_city_code) as cityname , (SELECT dist_name From dist WHERE city_code = place_city_code AND dist_code = place_dist_code) as distname\n" + 
				", add_road From activity_item WHERE activity_id = ? ;";

		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = DriverManager.getConnection(DbUtl.URL, DbUtl.USER, DbUtl.PASSWORD);
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setInt(2, id);
			ps.setInt(3, id);
			ps.setInt(4, id);
			ps.setInt(5, id);
			ps.setInt(6, id);
			ps.setInt(7, id);
			ps.setInt(8, id);
			ps.setInt(9, id);
			ps.setInt(10, id);
			ResultSet rs = ps.executeQuery();
			ActItem actItem = null;
			while (rs.next()) {
				int ItemNo = rs.getInt("item_no");
				byte[] actPhoto = rs.getBytes("act_photo");
				Base64.Encoder encoder = Base64.getEncoder();
			    String picBase64 =  encoder.encodeToString(actPhoto);
				//byte[] encodedImage = Base64.encodeBase64(actPhoto);
				//byte[] image = Base64.getMimeDecoder().decode(actPhoto);
				//String imageBase64 = Base64.encodeToString(actPhoto, Base64.DEFAULT);
				String activityTitle = rs.getString("act_title");
				int activityFee = rs.getInt("activity_fee");
				String itemDesc = rs.getString("item_desc");
				int goodCount = rs.getInt("good_count");
				int collectionCount = rs.getInt("collection_count");
				int msgCount = 0;
				int memberNo = rs.getInt("member_no");
				byte[] memberPhoto = rs.getBytes("member_photo");
				Base64.Encoder encoder2 = Base64.getEncoder();
			    String memberpicBase64 =  encoder2.encodeToString(memberPhoto);
				String memberName = rs.getString("member_name");
				int memberActCount = rs.getInt("memberActCount");
				int memberEvaCount = 0;
				String activityDescribe = rs.getString("discribe");
				String actItemDesc = rs.getString("act_item_desc");
				long regStartdate = rs.getTimestamp("reg_startdate").getTime();
				long regDuedate = rs.getTimestamp("reg_duedate").getTime();
				long activityStartdate = rs.getTimestamp("activity_startdate").getTime();
				int activityCount = rs.getInt("activity_count");
				String placeDesc = rs.getString("place_desc");
				String placeCityName = rs.getString("cityname");
				String placeDistName = rs.getString("distname");
				String addRoad = rs.getString("add_road");
				
				actItem = new ActItem(ItemNo, picBase64, activityTitle, activityFee, itemDesc, goodCount
		                , collectionCount, msgCount, memberNo, memberpicBase64, memberName, memberActCount, memberEvaCount
		                , activityDescribe, actItemDesc, regStartdate, regDuedate
		                , activityStartdate, activityCount, placeDesc, placeCityName
		                , placeDistName, addRoad);
			}
			return actItem;
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

	public int insertReg(ActItem actItem) {
		int count = 0;
		String sql = "INSERT INTO activity_register"
				+ "(activity_id, item_no, member_no, reg_numbers, reg_date, reg_status)"
				+ "VALUES(?, ?, ?, ?, ?, ?);";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(DbUtl.URL, DbUtl.USER,
					DbUtl.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, actItem.getActivityId());
			ps.setInt(2, actItem.getItemNo());
			ps.setInt(3, actItem.getMemberNo());
			ps.setInt(4, actItem.getRegNumbers());
			 
			ps.setString(5, actItem.getRegDate());
			ps.setInt(6, actItem.getRegStatus());
			count = ps.executeUpdate();
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
		return count;
	}

}
