package com.project.pp.parentparadise.lin;

/**
 * Created by lin on 2017/12/10.
 */

public class ActRecommend {

    private int image;
    private String title;

    public ActRecommend() {
        super();
    }

    public ActRecommend(int image, String title) {
        super();
        this.image = image;
        this.title = title;

    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
