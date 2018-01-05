package com.project.pp.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.project.pp.utl.Common;
import com.project.pp.utl.DbUtl;

/**
 * Servlet implementation class RegisterSevlet
 */
@SuppressWarnings("serial")
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	//private static final long serialVersionUID = 1L;
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		BufferedReader br = request.getReader();
		StringBuffer jsonIn = new StringBuffer();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		System.out.println("input: " + jsonIn);
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(),
				JsonObject.class);
		
		Connection conn = null;
		String error_msg = "";
		
		try {
			conn = DbUtl.getConnection();
			ArrayList rc = doCheck(conn, jsonObject);
			if ((boolean) rc.get(0)) {
				rc = doRegister(conn, jsonObject);
			}
			jsonOut(response, (boolean) rc.get(0), (String) rc.get(1));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			error_msg = "發生系統錯誤！請洽詢系統管理員！";
			jsonOut(response, false, error_msg);
			e.printStackTrace();
		} finally {
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private ArrayList doCheck(Connection conn, JsonObject jsonObject) {
		// TODO Auto-generated method stub
		ArrayList rc = new ArrayList();
		ArrayList<String> msgList = new ArrayList<>();
		boolean isCheckOk = true;
		String mail = jsonObject.get("mail").getAsString();
		String accCode = jsonObject.get("accCode").getAsString();
		String sql = "SELECT count(*) FROM account WHERE acc_mail = ? ";
		String sql2 = "SELECT count(*) FROM account WHERE acc_code = ? ";
		System.out.println("SQL command: " + sql);
		PreparedStatement pstamt = null;
		try {
			pstamt = conn.prepareStatement(sql);
			pstamt.setString(1, mail);
			ResultSet rs1 = pstamt.executeQuery();
			rs1.next();
			if (rs1.getInt(1) > 0) {
				isCheckOk = false;
				msgList.add("電子郵件信箱");
			}
			
			pstamt = conn.prepareStatement(sql2);
			pstamt.setString(1, accCode);
			ResultSet rs2 = pstamt.executeQuery();
			rs2.next();
			if (rs2.getInt(1) > 0) {
				isCheckOk = false;
				msgList.add("會員代號");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String error_msg = "";
		if (!isCheckOk) {
			error_msg = Common.joinArrayListString(msgList, "、") + "已經被註冊了！";
		}
		rc.add(isCheckOk);
		rc.add(error_msg);
		return rc;
	}

	private ArrayList<Object> doRegister(Connection conn, JsonObject jsonObject) {
		// TODO Auto-generated method stub
		ArrayList<Object> rc = new ArrayList<Object>();
		boolean isRegisterOk = false;
		String return_msg = "";
		
		String accCode = jsonObject.get("accCode").getAsString();
        String lastName = jsonObject.get("lastName").getAsString();
        String firstName = jsonObject.get("firstName").getAsString();
        String email = jsonObject.get("mail").getAsString();
        String password = jsonObject.get("password").getAsString();
        String salt = email.replace("@", "");
        salt = salt.replace(".", "");
        String hashPassword = Common.encrypt(password + salt);
        String headImage = jsonObject.get("headImage").getAsString();
        byte[] image = Base64.getMimeDecoder().decode(headImage);
        
        System.out.println("encryptPassword: " + hashPassword + ", password: " + password + ", salt: " + salt);
        
		String sql = "INSERT INTO account (acc_mail, acc_code, acc_type, is_vip, password, status, cr_date)"
				+ " VALUES (?, ?, 'O', 0, ?, 0, ?)";
		System.out.println("SQL command: " + sql);
		
		PreparedStatement pstamt = null;
		try { 
			pstamt = conn.prepareStatement(sql);
			pstamt.setString(1, email);
			pstamt.setString(2, accCode);
			pstamt.setString(3, hashPassword);
			pstamt.setDate(4, Common.getCurrentDateTime());
			int count = pstamt.executeUpdate();
			
			if (count > 0) {
				sql = "INSERT INTO member (acc_code, last_name, first_name, mb_photo) VALUES (?, ? ,?, ?)";
				pstamt = conn.prepareStatement(sql);
				pstamt.setString(1, accCode);
				pstamt.setString(2, lastName);
				pstamt.setString(3, firstName);
				pstamt.setBytes(4, image);
				count = pstamt.executeUpdate();
				
				if (count > 0) {
					isRegisterOk = true;
					return_msg = "註冊成功！";
					conn.commit();
				} else {
					isRegisterOk = false;
					return_msg = "註冊失敗！";
					conn.rollback();
				}
				pstamt.close();
			}
		} catch (Exception e) {
			isRegisterOk = false;
			return_msg = "系統錯誤！請洽詢系統管理員！";
			e.printStackTrace();
		} finally {
			try {
				if (pstamt != null)
					pstamt.close();
			} catch (SQLException e) {
				System.out.println("url: " + DbUtl.URL + ", user: " + DbUtl.USER + ", pwd: " + DbUtl.PASSWORD);
				e.printStackTrace();
			}
		}
		rc.add(isRegisterOk);
		rc.add(return_msg);
		return rc;
	}
	
	private void jsonOut (HttpServletResponse response, boolean rv, String msg) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("return_value", rv);
		jsonObject.addProperty("return_msg", msg);
		response.setContentType(CONTENT_TYPE);
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(jsonObject.toString());
			System.out.println("output: " + jsonObject.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
