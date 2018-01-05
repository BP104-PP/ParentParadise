package com.project.pp.freya;

public class ChatList {

	//private String first_name;
    private String chat_message;
    //private  int chat_message_id;



   

//	public void CommunityChatList( int chat_message_id , String chat_message) {
//       
//        this.chat_message_id=chat_message_id;
//       // this. first_name= first_name;
//        this.chat_message = chat_message;
//
//
//    }

//    public String getFirst_name() {
//        return first_name;
//    }
//
//    public void setFirst_name(String first_name) {
//        this.first_name = first_name;
//    }


    public String getChat_message() {
        return chat_message;
    }

  

	public ChatList(String chat_message) {
		super();
		this.chat_message = chat_message;
	}

	public void setChat_message(String chat_message) {
        this.chat_message = chat_message;
    }

//    public int getChat_message_id() {
//        return chat_message_id;
//    }
//
//    public void setChat_message_id(int chat_message_id) {
//        this.chat_message_id = chat_message_id;
//    }
}
