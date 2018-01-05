package com.project.pp.lin;

public class Act {
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

}
