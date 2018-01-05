package com.project.pp.parentparadise.ppmain;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.project.pp.parentparadise.utl.Common;
import com.project.pp.parentparadise.utl.MyTask;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chungnan on 2017/12/27.
 */

public class Member {
    private final static String TAG = "Member";
    private MyTask task;

    private int member_no;
    private String acc_code;
    private String last_name;
    private String first_name;
    private String mb_sex;
    private String mb_phone;
    private String city_code;
    private String dist_code;
    private Blob mb_photo;

    public Member() {

    }

    public Member(String idStr) {
        this.member_no = 3;
        this.acc_code = "WANG.01";
        this.first_name = "大同";
        this.last_name = "王";
    }

    public Member(int member_no) {
        setMemberInfo(member_no);
    }


    private void setMemberInfo(int member_no) {
        String url = Common.URL + "/MemberServlet";
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("memberNo", member_no);
            String jsonOut = jsonObject.toString();

            task = new MyTask(url, jsonOut);
            String jsonIn = task.execute().get();
            Log.d(TAG, jsonIn);
            Gson gson = new Gson();
            Member mb = gson.fromJson(jsonIn, Member.class);
            setMember_no(member_no);
            setAcc_code(mb.acc_code);
            setMb_sex(mb.mb_sex);
            setFirst_name(mb.first_name);
            setLast_name(mb.last_name);
            setMb_phone(mb.mb_phone);
            setCity_code(mb.city_code);
            setDist_code(mb.dist_code);

        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    public List<Member> getFriends() {
        List<Member> friends = new ArrayList<Member>();
        Member friend1 = new Member(2);
        Member friend2 = new Member("WANG.01");
        friends.add(friend1);
        friends.add(friend2);
        return friends;
    }

    public int getMember_no() {
        return member_no;
    }

    public void setMember_no(int member_no) {
        this.member_no = member_no;
    }

    public String getAcc_code() {
        return acc_code;
    }

    public void setAcc_code(String acc_code) {
        this.acc_code = acc_code;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMb_sex() {
        return mb_sex;
    }

    public void setMb_sex(String mb_sex) {
        this.mb_sex = mb_sex;
    }

    public String getMb_phone() {
        return mb_phone;
    }

    public void setMb_phone(String mb_phone) {
        this.mb_phone = mb_phone;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getDist_code() {
        return dist_code;
    }

    public void setDist_code(String dist_code) {
        this.dist_code = dist_code;
    }
}
