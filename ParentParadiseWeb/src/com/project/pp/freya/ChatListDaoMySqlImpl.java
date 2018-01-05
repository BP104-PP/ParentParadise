package com.project.pp.freya;

	//import idv.ron.server.main.Common;

	import java.io.UnsupportedEncodingException;
	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.util.ArrayList;
	import java.util.List;

	public class ChatListDaoMySqlImpl {

	    public ChatListDaoMySqlImpl() {
	        super();
	        try {
	            Class.forName("com.mysql.jdbc.Driver");
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	    }

//	    public int insert(Spot spot, byte[] image) {
//	        int count = 0;
//	        String sql = "INSERT INTO Spot"
//	                + "(name, phoneNo, address, latitude, longitude, image) "
//	                + "VALUES(?, ?, ?, ?, ?, ?);";
//	        Connection connection = null;
//	        PreparedStatement ps = null;
//	        try {
//	            connection = DriverManager.getConnection(Common.URL, Common.USER,
//	                    Common.PASSWORD);
//	            ps = connection.prepareStatement(sql);
//	            ps.setString(1, spot.getName());
//	            ps.setString(2, spot.getPhoneNo());
//	            ps.setString(3, spot.getAddress());
//	            ps.setDouble(4, spot.getLatitude());
//	            ps.setDouble(5, spot.getLongitude());
//	            ps.setBytes(6, image);
//	            count = ps.executeUpdate();
//	        } catch (SQLException e) {
//	            e.printStackTrace();
//	        } finally {
//	            try {
//	                if (ps != null) {
//	                    // When a Statement object is closed,
//	                    // its current ResultSet object is also closed
//	                    ps.close();
//	                }
//	                if (connection != null) {
//	                    connection.close();
//	                }
//	            } catch (SQLException e) {
//	                e.printStackTrace();
//	            }
//	        }
//	        return count;
//	    }
	//
//	    public int update(Spot spot, byte[] image) {
//	        int count = 0;
//	        String sql = "UPDATE Spot SET name = ?, phoneNo = ?, address = ?, latitude = ?, longitude = ?, image = ? WHERE id = ?;";
//	        Connection connection = null;
//	        PreparedStatement ps = null;
//	        try {
//	            connection = DriverManager.getConnection(Common.URL, Common.USER,
//	                    Common.PASSWORD);
//	            ps = connection.prepareStatement(sql);
//	            ps.setString(1, spot.getName());
//	            ps.setString(2, spot.getPhoneNo());
//	            ps.setString(3, spot.getAddress());
//	            ps.setDouble(4, spot.getLatitude());
//	            ps.setDouble(5, spot.getLongitude());
//	            ps.setBytes(6, image);
//	            ps.setInt(7, spot.getId());
//	            count = ps.executeUpdate();
//	        } catch (SQLException e) {
//	            e.printStackTrace();
//	        } finally {
//	            try {
//	                if (ps != null) {
//	                    // When a Statement object is closed,
//	                    // its current ResultSet object is also closed
//	                    ps.close();
//	                }
//	                if (connection != null) {
//	                    connection.close();
//	                }
//	            } catch (SQLException e) {
//	                e.printStackTrace();
//	            }
//	        }
//	        return count;
//	    }
	//
//	    @Override
//	    public int delete(int id) {
//	        int count = 0;
//	        String sql = "DELETE FROM Spot WHERE id = ?;";
//	        Connection connection = null;
//	        PreparedStatement ps = null;
//	        try {
//	            connection = DriverManager.getConnection(Common.URL, Common.USER,
//	                    Common.PASSWORD);
//	            ps = connection.prepareStatement(sql);
//	            ps.setInt(1, id);
//	            count = ps.executeUpdate();
//	        } catch (SQLException e) {
//	            e.printStackTrace();
//	        } finally {
//	            try {
//	                if (ps != null) {
//	                    // When a Statement object is closed,
//	                    // its current ResultSet object is also closed
//	                    ps.close();
//	                }
//	                if (connection != null) {
//	                    connection.close();
//	                }
//	            } catch (SQLException e) {
//	                e.printStackTrace();
//	            }
//	        }
//	        return count;
//	    }
	//
//	    @Override
//	    public byte[] getImage(int id) {
//	        String sql = "SELECT image FROM Spot WHERE id = ?;";
//	        Connection connection = null;
//	        PreparedStatement ps = null;
//	        byte[] image = null;
//	        try {
//	            connection = DriverManager.getConnection(Common.URL, Common.USER,
//	                    Common.PASSWORD);
//	            ps = connection.prepareStatement(sql);
//	            ps.setInt(1, id);
//	            ResultSet rs = ps.executeQuery();
//	            if (rs.next()) {
//	                image = rs.getBytes(1);
//	            }
//	        } catch (SQLException e) {
//	            e.printStackTrace();
//	        } finally {
//	            try {
//	                if (ps != null) {
//	                    // When a Statement object is closed,
//	                    // its current ResultSet object is also closed
//	                    ps.close();
//	                }
//	                if (connection != null) {
//	                    connection.close();
//	                }
//	            } catch (SQLException e) {
//	                e.printStackTrace();
//	            }
//	        }
//	        return image;
//	    }
	//
//	    @Override
//	    public Spot findById(int id) {
//	        String sql = "SELECT name, phoneNo, address, latitude, longitude FROM Spot WHERE id = ?;";
//	        Connection conn = null;
//	        PreparedStatement ps = null;
//	        Spot spot = null;
//	        try {
//	            conn = DriverManager.getConnection(Common.URL, Common.USER,
//	                    Common.PASSWORD);
//	            ps = conn.prepareStatement(sql);
//	            ps.setInt(1, id);
//	            ResultSet rs = ps.executeQuery();
//	            if (rs.next()) {
//	                String name = rs.getString(1);
//	                String phoneNo = rs.getString(2);
//	                String address = rs.getString(3);
//	                double latitude = rs.getDouble(4);
//	                double longitude = rs.getDouble(5);
//	                spot = new Spot(id, name, phoneNo, address, latitude, longitude);
//	            }
//	        } catch (SQLException e) {
//	            e.printStackTrace();
//	        } finally {
//	            try {
//	                if (ps != null) {
//	                    ps.close();
//	                }
//	                if (conn != null) {
//	                    conn.close();
//	                }
//	            } catch (SQLException e) {
//	                e.printStackTrace();
//	            }
//	        }
//	        return spot;
//	    }

	    public List<ChatList> getAll(int chat_messgae_id) {
	        String sql = "SELECT chat_message_id,chat_message \n" + 
	        		"FROM chat_message;";  
//	        		" Where member.member_no=?;";
	        Connection connection = null;
	        PreparedStatement ps = null;
	       
	        ArrayList<ChatList> chatList = new ArrayList<ChatList>();
	        try {
	            connection = DriverManager.getConnection(Common.URL, Common.USER,
	                    Common.PASSWORD);
	            ps = connection.prepareStatement(sql);
	            //ps.setInt(1, chat_messgae_id);
//	            ps.setString(2,chat_message);
	            ResultSet rs = ps.executeQuery();
	            while (rs.next()) {
	                //int chat_message_id  = rs.getInt(1);
	                String chat_message = rs.getString(2);
	                //int is_block = rs.getInt(3);
	                ChatList chat = new ChatList(chat_message);
	                chatList.add(chat);
	            }
	            return chatList;
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
	        return chatList;
	    }
	    
	}

		



