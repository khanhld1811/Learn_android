package com.duykhanh.a13l01_jsondemo.view.ex03_GetContact.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Comment implements Serializable {

    private boolean is_private;
    private int count;
    private int attachment_id;
    private String creator;
    private String time;
    private String author;
    private int bug_id;
    private String text;
    private int id;
    private String creation_time;
    private String raw_text;

    public Comment() {
    }

    public Comment(boolean is_private, int count, int attachment_id, String creator, String time, String author, int bug_id, String text, int id, String creation_time, String raw_text) {
        this.is_private = is_private;
        this.count = count;
        this.attachment_id = attachment_id;
        this.creator = creator;
        this.time = time;
        this.author = author;
        this.bug_id = bug_id;
        this.text = text;
        this.id = id;
        this.creation_time = creation_time;
        this.raw_text = raw_text;
    }

    public boolean isIs_private() {
        return is_private;
    }

    public void setIs_private(boolean is_private) {
        this.is_private = is_private;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getAttachment_id() {
        return attachment_id;
    }

    public void setAttachment_id(int attachment_id) {
        this.attachment_id = attachment_id;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getBug_id() {
        return bug_id;
    }

    public void setBug_id(int bug_id) {
        this.bug_id = bug_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreation_time() {
        return creation_time;
    }

    public void setCreation_time(String creation_time) {
        this.creation_time = creation_time;
    }

    public String getRaw_text() {
        return raw_text;
    }

    public void setRaw_text(String raw_text) {
        this.raw_text = raw_text;
    }
}
