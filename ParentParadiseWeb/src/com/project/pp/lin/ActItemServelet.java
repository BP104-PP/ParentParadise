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
@WebServlet("/ActItemServelet")
public class ActItemServelet extends HttpServlet{
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ActItemDao actItemDao = new ActItemDao();
		ActItem actItem = actItemDao.getItem(22);
		writeText(response, new Gson().toJson(actItem));
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
		ActItemDao actItemDao = new ActItemDao();
		String action = jsonObject.get("action").getAsString();
		if (action.equals("getItem")) {
			
			int id = jsonObject.get("id").getAsInt();
			ActItem actItem = actItemDao.getItem(id);
			
			writeText(response, gson.toJson(actItem));
		}else if(action.equals("addActReg")) {
			String actItemJson = jsonObject.get("actItem").getAsString();
			ActItem actItem = gson.fromJson(actItemJson, ActItem.class);
			int count = 0;
			
			count = actItemDao.insertReg(actItem);
			
			writeText(response, String.valueOf(count));
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
