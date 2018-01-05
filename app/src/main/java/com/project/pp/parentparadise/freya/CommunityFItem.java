package com.project.pp.parentparadise.freya;

/**
 * Created by mac on 2017/12/13.
 */



public class CommunityFItem {

    private int relation_member_no;
    private String first_name;
    private int is_block;
    public CommunityFItem(int relation_member_no, String first_name, int is_block) {
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

}