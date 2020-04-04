package com.ontecxmedia.ontecx.model;

public class GeneralNewsModel {


    private String title;
    private int thumbnail;
    private String desc;


    private String category;


    private String author;

    public GeneralNewsModel(String title, int thumbnail, String desc,String author,String category) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.desc = desc;
        this.author = author;
        this.category = category;

    }





    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
