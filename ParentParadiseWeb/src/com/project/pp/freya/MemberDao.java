package com.project.pp.freya;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

//import idv.ron.server.friends.Friend;
//import idv.ron.server.main.Common;

//import idv.ron.server.friends.Friend;
//import idv.ron.server.friends.MemberDaoMySqlImpl;

//import idv.ron.server.friends.Friend;
//import idv.ron.server.main.Common;

//import idv.ron.server.friends.Friend;

public class MemberDao {
	
	public String acc_code;
	
	public MemberDao() {
		super();
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

//	public int insert(Spot spot, byte[] image) {
//		int count = 0;
//		String sql = "INSERT INTO Spot"
//				+ "(name, phoneNo, address, latitude, longitude, image) "
//				+ "VALUES(?, ?, ?, ?, ?, ?);";
//		Connection connection = null;
//		PreparedStatement ps = null;
//		try {	
//			connection = DriverManager.getConnection(Common.URL, Common.USER,
//					Common.PASSWORD);
//			ps = connection.prepareStatement(sql);
//			ps.setString(1, spot.getName());
//			ps.setString(2, spot.getPhoneNo());
//			ps.setString(3, spot.getAddress());
//			ps.setDouble(4, spot.getLatitude());
//			ps.setDouble(5, spot.getLongitude());
//			ps.setBytes(6, image);
//			count = ps.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (ps != null) {
//					// When a Statement object is closed,
//					// its current ResultSet object is also closed
//					ps.close();
//				}
//				if (connection != null) {
//					connection.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return count;
//	}
//
//	public int update(Spot spot, byte[] image) {
//		int count = 0;
//		String sql = "UPDATE Spot SET name = ?, phoneNo = ?, address = ?, latitude = ?, longitude = ?, image = ? WHERE id = ?;";
//		Connection connection = null;
//		PreparedStatement ps = null;
//		try {
//			connection = DriverManager.getConnection(Common.URL, Common.USER,
//					Common.PASSWORD);
//			ps = connection.prepareStatement(sql);
//			ps.setString(1, spot.getName());
//			ps.setString(2, spot.getPhoneNo());
//			ps.setString(3, spot.getAddress());
//			ps.setDouble(4, spot.getLatitude());
//			ps.setDouble(5, spot.getLongitude());
//			ps.setBytes(6, image);
//			ps.setInt(7, spot.getId());
//			count = ps.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (ps != null) {
//					// When a Statement object is closed,
//					// its current ResultSet object is also closed
//					ps.close();
//				}
//				if (connection != null) {
//					connection.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return count;
//	}
//
//	@Override
//	public int delete(int id) {
//		int count = 0;
//		String sql = "DELETE FROM Spot WHERE id = ?;";
//		Connection connection = null;
//		PreparedStatement ps = null;
//		try {
//			connection = DriverManager.getConnection(Common.URL, Common.USER,
//					Common.PASSWORD);
//			ps = connection.prepareStatement(sql);
//			ps.setInt(1, id);
//			count = ps.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (ps != null) {
//					// When a Statement object is closed,
//					// its current ResultSet object is also closed
//					ps.close();
//				}
//				if (connection != null) {
//					connection.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return count;
//	}
//
//	@Override
//	public byte[] getImage(int id) {
//		String sql = "SELECT image FROM Spot WHERE id = ?;";
//		Connection connection = null;
//		PreparedStatement ps = null;
//		byte[] image = null;
//		try {
//			connection = DriverManager.getConnection(Common.URL, Common.USER,
//					Common.PASSWORD);
//			ps = connection.prepareStatement(sql);
//			ps.setInt(1, id);
//			ResultSet rs = ps.executeQuery();
//			if (rs.next()) {
//				image = rs.getBytes(1);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (ps != null) {
//					// When a Statement object is closed,
//					// its current ResultSet object is also closed
//					ps.close();
//				}
//				if (connection != null) {
//					connection.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return image;
//	}
//
//	@Override
//	public Spot findById(int id) {
//		String sql = "SELECT name, phoneNo, address, latitude, longitude FROM Spot WHERE id = ?;";
//		Connection conn = null;
//		PreparedStatement ps = null;
//		Spot spot = null;
//		try {
//			conn = DriverManager.getConnection(Common.URL, Common.USER,
//					Common.PASSWORD);
//			ps = conn.prepareStatement(sql);
//			ps.setInt(1, id);
//			ResultSet rs = ps.executeQuery();
//			if (rs.next()) {
//				String name = rs.getString(1);
//				String phoneNo = rs.getString(2);
//				String address = rs.getString(3);
//				double latitude = rs.getDouble(4);
//				double longitude = rs.getDouble(5);
//				spot = new Spot(id, name, phoneNo, address, latitude, longitude);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (ps != null) {
//					ps.close();
//				}
//				if (conn != null) {
//					conn.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return spot;
//	}

	public List<MemberSearch> getAll(String acc_code) {
		String sql = "SELECT first_name,last_name FROM member WHERE acc_code = ?" ;
//		因為只需要input一個acc_code 所以只要一個問號 傳入只要一個參數
		Connection connection = null;
		PreparedStatement ps = null;
		List<MemberSearch> member = new ArrayList<MemberSearch>();
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USER,
					Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1,acc_code);//input acc_code
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String first_name = rs.getString(1);
				String last_name = rs.getString(2);
			//	rs parser
//				int is_block = rs.getInt(3);
				MemberSearch members = new MemberSearch(first_name,last_name);
				member.add(members);
			}
			return member;
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
		return member;
	}
}
