package com.project.pp.amber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;


@SuppressWarnings("serial")
@WebServlet("/ShareServlet")
public class ShareServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		Gson gson = new GsonBuilder() .setDateFormat("yyyy-MM-dd HH:mm").create();
		BufferedReader br = request.getReader();
		StringBuffer jsonIn = new StringBuffer();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		System.out.println("input: " + jsonIn);

		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		ShareDao shareDao = new ShareDao();
		String action = jsonObject.get("action").getAsString();

		if (action.equals("getAll")) {
			List<ShareData> shareList = shareDao.getAll();
			writeText(response, gson.toJson(shareList));
			
		} else if (action.equals("goodChecked")) {
			int memberNo = jsonObject.get("memberNo").getAsInt();
			int shareId = jsonObject.get("shareId").getAsInt();
			String goodType = jsonObject.get("goodType").getAsString();
			int goodChecked = shareDao.checkGood(goodType, shareId, memberNo);
			if (goodChecked == 0) {
				int count = shareDao.insertGood(goodType, shareId, memberNo);
				writeText(response, String.valueOf(count));
			}else {
				int count = shareDao.deleteGood(goodType, shareId, memberNo);
				writeText(response, String.valueOf(count));
			}
			writeText(response, gson.toJson(goodChecked));
			
		} else if (action.equals("getHeadPhoto")) {
			OutputStream os = response.getOutputStream();
			int memberNo = jsonObject.get("memberNo").getAsInt();
			int imageSize = jsonObject.get("imageSize").getAsInt();
			byte[] headPhoto = shareDao.getHeadPhoto(memberNo);
			if (headPhoto != null) {
				headPhoto = ImageUtil.shrink(headPhoto, imageSize);
				response.setContentType("image/jpeg");
				response.setContentLength(headPhoto.length);
			}
			os.write(headPhoto);
			
		}  else if (action.equals("getPhotoList")) {
			int shareId = jsonObject.get("shareId").getAsInt();
			List<ShareData> photoList = shareDao.getPhotoList(shareId);
			writeText(response, gson.toJson(photoList));
			
		} else if (action.equals("getPhoto")) {
				OutputStream os = response.getOutputStream();
				int photoNo = jsonObject.get("photoNo").getAsInt();
				int imageSize = jsonObject.get("imageSize").getAsInt();
				byte[] photos = shareDao.getPhoto(photoNo);
				if (photos != null) {
					photos = ImageUtil.shrink(photos, imageSize);
					response.setContentType("image/jpeg");
					response.setContentLength(photos.length);
				}
				os.write(photos);
			
		} else if (action.equals("addArticle")) {
			String shareJson = jsonObject.get("shareData").getAsString();
			ShareData shareData = gson.fromJson(shareJson, ShareData.class);
			int shareId = shareDao.insertData(shareData);
			writeText(response, String.valueOf(shareId));

		} else if (action.equals("upLoadArticlePhoto")) {
			int shareId = jsonObject.get("shareId").getAsInt();
			String imageBase64 = jsonObject.get("ImageBase64").getAsString();
			byte[] image = Base64.getMimeDecoder().decode(imageBase64);	
			int count = shareDao.insertPhoto(shareId, image);
			writeText(response, String.valueOf(count));
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ShareDao shareDAO = new ShareDao();
		List<ShareData> shareList = shareDAO.getAll();
		writeText(response, new Gson().toJson(shareList));
	}

	private void writeText(HttpServletResponse response, String outText) throws IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		// System.out.println("outText: " + outText);
		out.print(outText);
		System.out.println("output: " + outText);
	}
}
