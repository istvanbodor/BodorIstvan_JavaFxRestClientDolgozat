package com.example.bodoristvan_javafxrestclientdolgozat;

public class Books {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    private int id;
    private String title;
    private int pages;
    private boolean available;

    public Books(int id, int pages, String title, boolean available) {
        this.id = id;
        this.title = title;
        this.pages = pages;
        this.available = available;
    }
}
