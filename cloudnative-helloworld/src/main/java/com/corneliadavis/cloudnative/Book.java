package com.corneliadavis.cloudnative;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by cdavis on 12/31/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {

    private String author;
    private String title;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Book(String author, String title) {
        this.author = author;
        this.title = title;
    }
}
