package com.project.pp.freya;



public class MemberSearch {

//    private int member_no;
//    private String acc_code;
    private String first_name;
    private String last_name;

    public MemberSearch(String first_name, String last_name) {
       
        this.first_name=first_name;
        this.last_name=last_name;
    }

    public String getFirst_name() {
    return first_name;
}

public void setFirst_name(String first_name) {
this.first_name = first_name;
}

public String getLast_name() {
return last_name;
}

public void setLast_name(String last_name) {
this.last_name = last_name;
}

//public int getMember_no() {
//        return member_no;
//    }
//
//    public void setMember_no(int member_no) {
//        this.member_no = member_no;
//    }
//
//    public String getAcc_code() {
//        return acc_code;
//    }
//
//    public void setAcc_code(String acc_code) {
//        this.acc_code = acc_code;
//    }

}