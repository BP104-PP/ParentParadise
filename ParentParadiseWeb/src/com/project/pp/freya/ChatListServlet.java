package com.project.pp.freya;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@SuppressWarnings("serial")
@WebServlet("/ChatListServlet")
public class ChatListServlet extends HttpServlet {
private final static String CONTENT_TYPE = "text/html; charset=utf-8";

public void doPost(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {
request.setCharacterEncoding("utf-8");
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
ChatListDaoMySqlImpl chatDao = new ChatListDaoMySqlImpl();
String action = jsonObject.get("action").getAsString();

if (action.equals("getAll")) {
//String first_name = jsonObject.get("first_name").getAsString();
int chat_message_id = jsonObject.get("chat_message_id").getAsInt();
List<ChatList>chatList = chatDao.getAll(chat_message_id);
writeText(response, gson.toJson(chatList));
} 
//else if (action.equals("getImage")) {
//OutputStream os = response.getOutputStream();
//int id = jsonObject.get("id").getAsInt();
//int imageSize = jsonObject.get("imageSize").getAsInt();
//byte[] image = friendDao.getImage(id);
//if (image != null) {
//image = ImageUtil.shrink(image, imageSize);
//response.setContentType("image/jpeg");
//response.setContentLength(image.length);
//}
//os.write(image);
//}
//} 
//else if (action.equals("friendInsert") || action.equals("friendUpdate")) {
//String friendJson = jsonObject.get("friend").getAsString();
//Friend friend = gson.fromJson(friendJson, Friend.class);
//String imageBase64 = jsonObject.get("imageBase64").getAsString();
//byte[] image = Base64.getMimeDecoder().decode(imageBase64);;
//int count = 0;
//if (action.equals("friendInsert")) {
//count = friendDao.insert(friend, image);
//} else if (action.equals("friendUpdate")) {
//count = friendDao.update(friend, image);
//}
//writeText(response, String.valueOf(count));
//} else if (action.equals("friendDelete")) {
//String friendJson = jsonObject.get("friend").getAsString();
//Friend friend = gson.fromJson(friendJson, Friend.class);
//int count = friendDao.delete(friend.getId());
//writeText(response, String.valueOf(count));
//} else if (action.equals("findById")) {
//int id = jsonObject.get("id").getAsInt();
//Friend friend = friendDao.findById(id);
//writeText(response, gson.toJson(friend));
//} else {
//writeText(response, "");
//}
}

//public void doGet(HttpServletRequest request, HttpServletResponse response)
//throws ServletException, IOException {
//ChatDaoMySqlImpl chatDao = new ChatDaoMySqlImpl();
//List<ChatList> chatList = chatDao.getAll(1);
//writeText(response, new Gson().toJson(chatList));
//}

private void writeText(HttpServletResponse response, String outText)
throws IOException {
response.setContentType(CONTENT_TYPE);
PrintWriter out = response.getWriter();
// System.out.println("outText: " + outText);
out.print(outText);
System.out.println("output: " + outText);
}
}

