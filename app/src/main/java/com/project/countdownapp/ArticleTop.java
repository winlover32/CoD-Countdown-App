package com.project.countdownapp;

/**
 * Created by Luke on 15/09/2016.
 */
public class ArticleTop {
    String title;
    String date;
    String image;
    String link;

    public ArticleTop(String title, String link, String image, String date) {
        this.title = title;
        this.link = link;
        this.image = image;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "ArticleTop{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", image='" + image + '\'' +
                ", link='" + link + '\'' +
                '}';
    }

    public String getImage() {
        return image;
    }

    public String getLink() {
        return link;
    }
}
