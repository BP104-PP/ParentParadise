package com.project.pp.lin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.project.pp.utl.ImageUtil;



@SuppressWarnings("serial")
@WebServlet("/ActServlet")
public class ActServelet extends HttpServlet{
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ActDao actDao = new ActDao();
		List<Act> actList = actDao.getAll();
		//List<Act> actList = actDao.getSort(9);
		writeText(response, new Gson().toJson(actList));
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
		ActDao actDao = new ActDao();
		String action = jsonObject.get("action").getAsString();

		if (action.equals("getAll")) {
			List<Act> actList = actDao.getAll();
			writeText(response, gson.toJson(actList));
		}else if(action.equals("getSort")) {
			int actType = jsonObject.get("actType").getAsInt();
			System.out.println(actType);
			List<Act> actList = actDao.getSort(actType);
			writeText(response, gson.toJson(actList));
		}else if(action.equals("getHistory")) {
			int memberNumber = jsonObject.get("memberNumber").getAsInt();
			System.out.println(memberNumber);
			List<Act> actList = actDao.getHistory(memberNumber);
			writeText(response, gson.toJson(actList));
		}else if(action.equals("getWatch")) {
			int memberNumber = jsonObject.get("memberNumber").getAsInt();
			String recordType = jsonObject.get("goodType").getAsString();
			List<Act> actList = actDao.getWatch(memberNumber, recordType);
			writeText(response, gson.toJson(actList));
		}else if (action.equals("getImage")) {
			OutputStream os = response.getOutputStream();
			int id = jsonObject.get("id").getAsInt();
			int imageSize = jsonObject.get("imageSize").getAsInt();
			byte[] image = actDao.getImage(id);
			if (image != null) {
				image = ImageUtil.shrink(image, imageSize);
				response.setContentType("image/png");
				response.setContentLength(image.length);
			}
			os.write(image);
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
