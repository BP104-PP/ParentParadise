package com.project.pp.amber;



public class ShareData {
    private int shareId;
    private int memberNo;
    private String postDate;
    private String shareType;
    private String content;
    private int head;
    private String lastName;
    private String firstName;
    private int photoNo;
    private String bookName;
    private String author;
    private String publisher;
    private String isbn;
    private int goodCount;
    private int messageCount;
    private String actID;
    private String message;
    private String image;


    public ShareData(int photoNo){
        this.photoNo = photoNo;
    }

    public ShareData(int shareId, String image){
        this.shareId = shareId;
        this.image = image;
        
    }

    //Add Share Article
    public ShareData(int memberNo, String shareType, String content, String isbn ){
        this.memberNo = memberNo;
        this.shareType = shareType;
        this.content = content;
        this.isbn = isbn;
    }

    //Get ShareSweet Data
    public ShareData(int shareId, int memberNo, String postDate, String content, int goodCount, String lastName, String firstName) {
        this.memberNo = memberNo;
        this.shareId = shareId;
        this.postDate = postDate;
        this.content = content;
        this.goodCount = goodCount;
        this.lastName = lastName;
        this.firstName = firstName;
    }


    public String getShareType() {
        return shareType;
    }

    public void setShareType(String shareType) {
        this.shareType = shareType;
    }

    public int getHead() {
        return head;
    }

    public void setHead(int head) {
        this.head = head;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getPhotoNo() {
        return photoNo;
    }

    public void setPhotoNo(int photoNo) {
        this.photoNo = photoNo;
    }

    public int getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(int memberNo) {
        this.memberNo = memberNo;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getShareId() {
        return shareId;
    }

    public void setShareId(int shareId) {
        this.shareId = shareId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(int goodCount) {
        this.goodCount = goodCount;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }

    public String getActID() {
        return actID;
    }

    public void setActID(String actID) {
        this.actID = actID;
    }

    public String getMessage() {
        return message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}