package com.project.pp.parentparadise.lin;

/**
 * Created by lin on 2018/1/1.
 */

public class ActItem {
    private int activityId;
    private int itemNo;
    private String activityTitle;
    private int activityFee;
    private String itemDesc;
    private int goodCount;
    private int collectionCount;
    private int msgCount;
    private int memberNo;
    private String memberName;
    private int memberActCount;
    private int memberEvaCount;
    private String activityDescribe;
    private String actItemDesc;
    private long regStartdate;
    private long regDuedate;
    private long activityStartdate;
    private int activityCount;
    private String placeDesc;
    private String placeCityName;
    private String placeDistName;
    private String addRoad;
    private byte[] actPhoto;
    private String actPic;
    private String actVipPic;

    private int regNumbers;
    private String regDate;
    private String cancelDate;
    private int regStatus;



    public ActItem(int itemNo, String actPic, String activityTitle, int activityFee, String itemDesc, int goodCount
            , int collectionCount, int msgCount, int memberNo, String actVipPic, String memberName, int memberActCount, int memberEvaCount
            , String activityDescribe, String actItemDesc, long regStartdate, long regDuedate
            , long activityStartdate, int activityCount, String placeDesc, String placeCityName
            , String placeDistName, String addRoad){
        this.itemNo = itemNo;
        this.actPic = actPic;
        this.activityTitle = activityTitle;
        this.activityFee = activityFee;
        this.itemDesc = itemDesc;
        this.goodCount = goodCount;
        this.collectionCount = collectionCount;
        this.msgCount = msgCount;
        this.memberNo = memberNo;
        this.actVipPic = actVipPic;
        this.memberName = memberName;
        this.memberActCount = memberActCount;
        this.memberEvaCount = memberEvaCount;
        this.activityDescribe = activityDescribe;
        this.actItemDesc = actItemDesc;
        this.activityCount = activityCount;
        this.placeDesc = placeDesc;
        this.placeCityName = placeCityName;
        this.placeDistName = placeDistName;
        this.addRoad = addRoad;
        this.regStartdate = regStartdate;
        this.regDuedate = regDuedate;
        this.activityStartdate = activityStartdate;
    }

    public ActItem(int activityId, int itemNo, int memberNo, int regNumbers, String regDate,
                   int regStatus){
        this.activityId = activityId;
        this.itemNo = itemNo;
        this.memberNo = memberNo;
        this.regNumbers = regNumbers;
        this.regDate = regDate;
        this.regStatus = regStatus;
    }

    public String getActVipPic() {
        return actVipPic;
    }

    public void setActVipPic(String actVipPic) {
        this.actVipPic = actVipPic;
    }

    public int getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(int memberNo) {
        this.memberNo = memberNo;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public int getItemNo() {
        return itemNo;
    }

    public void setItemNo(int itemNo) {
        itemNo = itemNo;
    }

    public String getActivityTitle() {
        return activityTitle;
    }

    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }

    public int getActivityFee() {
        return activityFee;
    }

    public void setActivityFee(int activityFee) {
        this.activityFee = activityFee;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
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

    public int getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(int msgCount) {
        this.msgCount = msgCount;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public int getMemberActCount() {
        return memberActCount;
    }

    public void setMemberActCount(int memberActCount) {
        this.memberActCount = memberActCount;
    }

    public int getMemberEvaCount() {
        return memberEvaCount;
    }

    public void setMemberEvaCount(int memberEvaCount) {
        this.memberEvaCount = memberEvaCount;
    }

    public String getActivityDescribe() {
        return activityDescribe;
    }

    public void setActivityDescribe(String activityDescribe) {
        this.activityDescribe = activityDescribe;
    }

    public String getActItemDesc() {
        return actItemDesc;
    }

    public void setActItemDesc(String actItemDesc) {
        this.actItemDesc = actItemDesc;
    }

    public long getRegStartdate() {
        return regStartdate;
    }

    public void setRegStartdate(long regStartdate) {
        this.regStartdate = regStartdate;
    }

    public long getRegDuedate() {
        return regDuedate;
    }

    public void setRegDuedate(long regDuedate) {
        this.regDuedate = regDuedate;
    }

    public long getActivityStartdate() {
        return activityStartdate;
    }

    public void setActivityStartdate(long activityStartdate) {
        this.activityStartdate = activityStartdate;
    }

    public int getActivityCount() {
        return activityCount;
    }

    public void setActivityCount(int activityCount) {
        this.activityCount = activityCount;
    }

    public String getPlaceDesc() {
        return placeDesc;
    }

    public void setPlaceDesc(String placeDesc) {
        this.placeDesc = placeDesc;
    }

    public String getPlaceCityName() {
        return placeCityName;
    }

    public void setPlaceCityName(String placeCityName) {
        this.placeCityName = placeCityName;
    }

    public String getPlaceDistName() {
        return placeDistName;
    }

    public void setPlaceDistName(String placeDistName) {
        this.placeDistName = placeDistName;
    }

    public String getAddRoad() {
        return addRoad;
    }

    public void setAddRoad(String addRoad) {
        this.addRoad = addRoad;
    }

    public byte[] getActPhoto() {
        return actPhoto;
    }

    public void setActPhoto(byte[] actPhoto) {
        this.actPhoto = actPhoto;
    }

    public String getActPic() {
        return actPic;
    }

    public void setActPic(String actPic) {
        this.actPic = actPic;
    }

    public int getRegNumbers() {
        return regNumbers;
    }

    public void setRegNumbers(int regNumbers) {
        this.regNumbers = regNumbers;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(String cancelDate) {
        this.cancelDate = cancelDate;
    }

    public int getRegStatus() {
        return regStatus;
    }

    public void setRegStatus(int regStatus) {
        this.regStatus = regStatus;
    }
}
