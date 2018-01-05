package com.project.pp.amber;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.project.pp.utl.DbUtl;

public class ShareDao {

	public ShareDao() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public int insertData(ShareData shareData) {
		ResultSet resultSet;
		int shareId = 0;
		String sql = "INSERT INTO share (member_no, post_date, share_type, content, isbn) VALUES(?, ?, ?, ?, ?) ";
		Connection connection = null;
		PreparedStatement ps = null;
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String postDate = sDateFormat.format(new java.util.Date());
		try {
			connection = DriverManager.getConnection(DbUtl.URL, DbUtl.USER, DbUtl.PASSWORD);
			ps = connection.prepareStatement(sql,ps.RETURN_GENERATED_KEYS);
			ps.setInt(1, shareData.getMemberNo());
			ps.setString(2, postDate);
			ps.setString(3, shareData.getShareType());
			ps.setString(4, shareData.getContent());
			ps.setString(5, shareData.getIsbn());
			ps.executeUpdate();
			resultSet = ps.getGeneratedKeys();
			resultSet.next();
			shareId = resultSet.getInt(1);
			System.out.println("shareId:" + shareId);
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
		return shareId;
	}
	
	public int insertPhoto( int shareId, byte[] image) {
		int count = 0;
		String sql = "INSERT INTO multi_media (share_Id, media_image) VALUES(?, ?) ";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(DbUtl.URL, DbUtl.USER, DbUtl.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, shareId);
			ps.setBytes(2, image);
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
	
	public List<ShareData> getAll() {
		String sql ="SELECT a.share_id, a.member_no,post_date, "
//				+ "( SELECT post_date,  date_format( post_date, '%Y -%m -%d  %H :%i') as new_date FROM PPDB.share), "
				+ "a.content, a.good_count, b.last_name, b.first_name "
				+ "  FROM share as a , member as b" 
				+ "  Where a.share_type = 'S' "
				+ "  AND a.member_no = b.member_no" 
				+ "  ORDER BY a.post_date DESC;" ;
		
		Connection connection = null;
		PreparedStatement ps = null;
		List<ShareData> shareList = new ArrayList<ShareData>();
		try {
			connection = DriverManager.getConnection(DbUtl.URL, DbUtl.USER,
					DbUtl.PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int shareId = rs.getInt("share_id");
				int memberNo = rs.getInt("member_no");
				String postDate = rs.getString("post_date");
				String content = rs.getString("content");
				int goodCount = rs.getInt("good_count");
				String lastName = rs.getString("last_name");
				String firstName = rs.getString("first_name");
				ShareData shares = new ShareData(shareId, memberNo, postDate, content, goodCount, lastName, firstName);
				shareList.add(shares);
			}
			return shareList;
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
		return shareList;
	}
	
	public byte[] getHeadPhoto(int memberNo) {
		String sql = "SELECT mb_photo FROM member WHERE member_no = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		byte[] headPhoto = null;
		try {
			connection = DriverManager.getConnection(DbUtl.URL, DbUtl.USER,
					DbUtl.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, memberNo);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				headPhoto = rs.getBytes(1);
			}
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
		return headPhoto;
	}
	
	public List<ShareData> getPhotoList(int shareId) {
		String sql ="SELECT mfile_no FROM multi_media WHERE share_id = ?;";	
		Connection connection = null;
		PreparedStatement ps = null;
		List<ShareData> photoList = new ArrayList<ShareData>();
		try {
			connection = DriverManager.getConnection(DbUtl.URL, DbUtl.USER,
					DbUtl.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, shareId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int photoNo = rs.getInt("mfile_no");
				ShareData photos = new ShareData(photoNo);
				photoList.add(photos);
			}
			return photoList;
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
		return photoList;
	}
	
	public byte[] getPhoto(int photoNo) {
		String sql = "SELECT media_image FROM multi_media WHERE mfile_no = ?;";
		Connection connection = null;
		PreparedStatement ps = null;
		byte[] photos = null;
		try {
			connection = DriverManager.getConnection(DbUtl.URL, DbUtl.USER,
					DbUtl.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, photoNo);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				photos = rs.getBytes(1);
			}
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
		return photos;
	}
}
