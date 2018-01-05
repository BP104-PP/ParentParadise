package com.project.pp.main;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.project.pp.utl.DbUtl;

public class Member {
	private int member_no;
	private String acc_code;
	private String last_name;
	private String first_name;
	private String mb_sex;
	private String mb_phone;
	private String city_code;
	private String dist_code;
	private Blob mb_photo;

	public Member getMemberInfo(int memberNo) {
		Member mb = new Member();

		String sql = "SELECT * " + "  FROM member" + " WHERE member_no = ?";
		System.out.println("SQL command: " + sql);
		Connection conn = null;
		PreparedStatement pstamt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DbUtl.URL, DbUtl.USER, DbUtl.PASSWORD);
			pstamt = conn.prepareStatement(sql);
			pstamt.setInt(1, memberNo);
			ResultSet rs = pstamt.executeQuery();
			rs.next();
			mb.setMember_no(memberNo);
			mb.setAcc_code(rs.getString("acc_code"));
			mb.setLast_name(rs.getString("last_name"));
			mb.setFirst_name(rs.getString("first_name"));
			mb.setMb_sex(rs.getString("mb_sex"));
			mb.setMb_phone(rs.getString("mb_phone"));
			mb.setCity_code(rs.getString("city_code"));
			mb.setDist_code(rs.getString("dist_code"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstamt != null)
					pstamt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println("url: " + DbUtl.URL + ", user: " + DbUtl.USER + ", pwd: " + DbUtl.PASSWORD);
				e.printStackTrace();
			}
		}
		return mb;
	}

	public int getMebmer_no() {
		return member_no;
	}

	public void setMember_no(int member_no) {
		this.member_no = member_no;
	}

	public String getAcc_code() {
		return acc_code;
	}

	public void setAcc_code(String acc_code) {
		this.acc_code = acc_code;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getMb_sex() {
		return mb_sex;
	}

	public void setMb_sex(String mb_sex) {
		this.mb_sex = mb_sex;
	}

	public String getMb_phone() {
		return mb_phone;
	}

	public void setMb_phone(String mb_phone) {
		this.mb_phone = mb_phone;
	}

	public String getCity_code() {
		return city_code;
	}

	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}

	public String getDist_code() {
		return dist_code;
	}

	public void setDist_code(String dist_code) {
		this.dist_code = dist_code;
	}

	public Date getMb_birthday() {
		return mb_birthday;
	}

	public void setMb_birthday(Date mb_birthday) {
		this.mb_birthday = mb_birthday;
	}

	private Date mb_birthday;

}
