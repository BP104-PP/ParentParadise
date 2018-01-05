package com.project.pp.freya;

public class Friend {
	private int relation_member_no;
	private String first_name;
	private int is_block;
//	private String acc_code;
//	private String last_name;
	
	
	public Friend(int relation_member_no, String first_name, int is_block) {
	super();
	this.relation_member_no = relation_member_no;
	this.first_name = first_name;
	this.is_block = is_block;
	}
	public int getRelation_member_no() {
	return relation_member_no;
	}
	public void setRelation_member_no(int relation_member_no) {
	this.relation_member_no = relation_member_no;
	}
	public String getFirst_name() {
	return first_name;
	}
	public void setFirst_name(String first_name) {
	this.first_name = first_name;
	}
	public int getIs_block() {
	return is_block;
	}
	public void setIs_block(int is_block) {
	this.is_block = is_block;
	}
//	public String getAcc_code() {
//		return acc_code;
//	}
//	public void setAcc_code(String acc_code) {
//		this.acc_code = acc_code;
//	}
//	public String getLast_name() {
//		return last_name;
//	}
//	public void setLast_name(String last_name) {
//		this.last_name = last_name;
//	}
}
