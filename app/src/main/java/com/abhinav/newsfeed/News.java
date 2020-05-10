package com.abhinav.newsfeed;

public class News {

    private String title;
    private String description;
    private String category;
    private String url_to_news;
    private String url_to_image;

    public News(String title, String description, String category,String url_to_news, String url_to_image){
        this.title = title;
        this.description = description;
        this.category = category;
        this.url_to_news = url_to_news;
        this.url_to_image = url_to_image;
    }

    public String getTitle(){
        return this.title;
    }

    public String getDescription(){
        return this.description;
    }

    public String getCategory() { return this.category; }

    public String getUrlToNews(){
        return this.url_to_news;
    }

    public String getUrlToImage(){
        return this.url_to_image;
    }
}
