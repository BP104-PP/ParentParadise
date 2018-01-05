package com.project.pp.parentparadise.lin;

import java.io.Serializable;

/**
 * Created by lin on 2017/12/10.
 */

public class Act implements Serializable {

    private int activityId;
    private int memberNo;
    private String activityTitle;
    private int activityType;
    private String cityCode;
    private String distCode;
    private String activityDescribe;
    private int goodCount;
    private int collectionCount;

    public Act(int activityId, int memberNo, String activityTitle, int activityType, String cityCode,
                    String distCode, String activityDescribe, int goodCount, int collectionCount) {
        this.activityId = activityId;
        this.memberNo = memberNo;
        this.activityTitle = activityTitle;
        this.activityType = activityType;
        this.cityCode = cityCode;
        this.distCode = distCode;
        this.activityDescribe = activityDescribe;
        this.goodCount = goodCount;
        this.collectionCount = collectionCount;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public int getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(int memberNo) {
        this.memberNo = memberNo;
    }

    public String getActivityTitle() {
        return activityTitle;
    }

    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }

    public int getActivityType() {
        return activityType;
    }

    public void setActivityType(int activityType) {
        this.activityType = activityType;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDistCode() {
        return distCode;
    }

    public void setDistCode(String distCode) {
        this.distCode = distCode;
    }

    public String getActivityDescribe() {
        return activityDescribe;
    }

    public void setActivityDescribe(String activityDescribe) {
        this.activityDescribe = activityDescribe;
    }

    public int getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(int goodCount) {
        this.goodCount = goodCount;
    }

    public int getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(int collectionCount) {
        this.collectionCount = collectionCount;
    }

//    private int image, like, watch, msg;
//    private String title;
//
//    public Act() {
//        super();
//    }
//
//    public Act(int image, String title, int like, int watch, int msg) {
//        super();
//        this.image = image;
//        this.title = title;
//        this.like = like;
//        this.watch = watch;
//        this.msg = msg;
//    }
//
//    public int getImage() {
//        return image;
//    }
//
//    public void setImage(int image) {
//        this.image = image;
//    }
//
//    public int getLike() {
//        return like;
//    }
//
//    public void setLike(int like) {
//        this.like = like;
//    }
//
//    public int getWatch() {
//        return watch;
//    }
//
//    public void setWatch(int watch) {
//        this.watch = watch;
//    }
//
//    public int getMsg() {
//        return msg;
//    }
//
//    public void setMsg(int msg) {
//        this.msg = msg;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
}
