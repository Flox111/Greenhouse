package com.maltsev.greenhouse.ui.greenhouse.model;

public class Note {

    private long id;
    private String date;
    private String text;

    public Note(long id, String date, String text) {
        this.id = id;
        this.date = date;
        this.text = text;
    }

    public Note(String date, String text) {
        this.date = date;
        this.text = text;
    }

    public Note() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
