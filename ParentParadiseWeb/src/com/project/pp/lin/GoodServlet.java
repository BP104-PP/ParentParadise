package com.project.pp.lin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;


@SuppressWarnings("serial")
@WebServlet("/GoodServlet")
public class GoodServlet extends HttpServlet{
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		GoodDao goodDao = new GoodDao();
		int goodChecked = goodDao.checkGood(1, 21,"A");
		//List<Act> actList = actDao.getSort(9);
		writeText(response, new Gson().toJson(goodChecked));
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		System.out.println("input: " + jsonIn);

		JsonObject jsonObject = gson.fromJson(jsonIn.toString(),
				JsonObject.class);
		GoodDao goodDao = new GoodDao();
		String action = jsonObject.get("action").getAsString();

		if (action.equals("checkGood")) {
			int memberNO = jsonObject.get("memberNo").getAsInt();
			//int memberNO = 1;
			int actID = jsonObject.get("actID").getAsInt();
			String goodType = jsonObject.get("goodType").getAsString();
			int goodChecked = goodDao.checkGood(memberNO, actID, goodType);
			Good good = new Good(goodType, actID, memberNO);
			if (goodChecked == 0) {
				int count = 0;
				count = goodDao.insertGood(good);
				writeText(response, String.valueOf(count));
			}else {
				
				int count = goodDao.deleteGood(memberNO, actID, goodType);
				writeText(response, String.valueOf(count));
			}
			writeText(response, gson.toJson(goodChecked));
		}else {
			writeText(response, "");
		}
	}
	
	private void writeText(HttpServletResponse response, String outText)
			throws IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(outText);
		System.out.println("output: " + outText);
	}
}
