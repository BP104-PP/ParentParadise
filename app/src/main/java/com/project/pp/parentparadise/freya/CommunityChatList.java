package com.project.pp.parentparadise.freya;

/**
 * Created by mac on 2017/12/13.
 */

public class CommunityChatList {

    //private String first_name;
    private String chat_message;
    //private int chat_message_id;




    public CommunityChatList(  String chat_message) {
        super();
        // this.chat_message_id = chat_message_id;
        //this. first_name= first_name;
        this.chat_message = chat_message;


    }



//    public int getChat_message_id(int chat_message_id){
//        return chat_message_id;}
//
//    public void setChat_message_id(String first_name) {
//        this.chat_message_id = chat_message_id;
//    }


    public String getChat_message() {
        return chat_message;
    }

    public void setChat_message(String chat_message) {
        this.chat_message = chat_message;
    }
}
