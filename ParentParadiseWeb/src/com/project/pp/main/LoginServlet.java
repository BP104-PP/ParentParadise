package com.project.pp.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import com.project.pp.utl.*;


/**
 * Servlet implementation class LoginServlet
 */
@SuppressWarnings("serial")
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	//private static final long serialVersionUID = 1L;
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
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
		jsonObject = checkUserValid(jsonObject);
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.println(jsonObject.toString());
		System.out.println("output: " + jsonObject.toString());
	}

	private JsonObject checkUserValid(JsonObject jsonObject) {
		// TODO Auto-generated method stub
		int memberNo = -1;
		boolean isUserValid = false;
		String login_mail = jsonObject.get("mail").getAsString();
		String login_password = jsonObject.get("password").getAsString();
		String sql = "SELECT a.password, m.member_no "
				   + "  FROM account as a, member as m "
				   + " WHERE a.acc_mail = ? AND a.acc_code = m.acc_code";
		System.out.println("SQL command: " + sql);
		Connection conn = null;
		PreparedStatement pstamt = null;
		String error_msg = "";
		try {
			Class.forName("com.mysql.jdbc.Driver"); 
			conn = DriverManager.getConnection(DbUtl.URL, DbUtl.USER,
					DbUtl.PASSWORD);
			//執行完login 刪除91~109行
			String sqlu = "SELECT acc_mail, password FROM account WHERE password = 'abcd123'";
			String sqlm = "UPDATE account SET password = ? WHERE acc_mail = ?";
			PreparedStatement pstm = null;
			pstm = conn.prepareStatement(sqlm);
			
			pstamt = conn.prepareStatement(sqlu);
			ResultSet rsu = pstamt.executeQuery();
			while(rsu.next()) {
				String p = rsu.getString("password");
				String m = rsu.getString("acc_mail");
				String s = m.replace("@", "");
		        s = s.replace(".", "");
				String ep = Common.encrypt(p + s);
				pstm.setString(1, ep);
				pstm.setString(2, m);
				int count = pstm.executeUpdate();
				System.out.println("update count:" + count);
			}
			pstm.close();
			
			pstamt = conn.prepareStatement(sql);
			pstamt.setString(1, login_mail);
			ResultSet rs = pstamt.executeQuery();
			if (rs.next()) {
				String password = rs.getString("password");
				String salt = login_mail.replace("@", "");
		        salt = salt.replace(".", "");
				String encryptPassword = Common.encrypt(login_password + salt);
				System.out.println("encryptPassword: " + encryptPassword + ", password: " + password);
		        
				if (encryptPassword.equals(password)) {
					isUserValid = true;
					memberNo = rs.getInt("member_no");
				} else {
					isUserValid = false;
					error_msg = "密碼錯誤！";
				}
			} else {
				isUserValid = false;
				error_msg = "會員帳戶不存在！";
			}
		} catch (Exception e) {
			isUserValid = false;
			error_msg = "系統錯誤！請洽詢系統管理員！";
			e.printStackTrace();
		} finally {
			try {
				if (pstamt != null)
					pstamt.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println("url: " + DbUtl.URL + ", user: " + DbUtl.USER + ", pwd: " + DbUtl.PASSWORD);
				e.printStackTrace();
			}
		}
		
		jsonObject = new JsonObject();
		jsonObject.addProperty("isUserValid", isUserValid);
		jsonObject.addProperty("memberNo", memberNo);
		jsonObject.addProperty("error_msg", error_msg);
		return jsonObject;
	}

}
