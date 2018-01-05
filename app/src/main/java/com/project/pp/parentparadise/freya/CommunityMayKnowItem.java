package com.project.pp.parentparadise.freya;

/**
 * Created by mac on 2017/12/14.
 */

public class CommunityMayKnowItem {

    private int mayknowimage;
    private String mayknowName;




    public CommunityMayKnowItem(int mayknowimage, String mayknowName) {
        super();
        this.mayknowimage = mayknowimage;
        this.mayknowName = mayknowName;


    }


    public int getImage() {
        return mayknowimage;
    }

    public void setImage(int mayknowimage) {
        this.mayknowimage = mayknowimage;
    }

    public String getMayknowName() {
        return mayknowName;
    }

    public void setMayknowName(String mayknowName) {
        this.mayknowName = mayknowName;
    }


}
