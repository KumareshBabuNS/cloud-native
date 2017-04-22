package com.corneliadavis.cloudnative;

/**
 * Created by cdavis on 12/28/16.
 */
public class Greeting {

    private String greeting;
    private String specialization;
    private Book[] books;

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public Book[] getBooks() {
        return books;
    }

    public void setBooks(Book[] books) {
        this.books = books;
    }

}
