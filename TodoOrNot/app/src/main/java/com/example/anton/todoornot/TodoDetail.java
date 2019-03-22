package com.example.anton.todoornot;

/**
 * Created by anton on 3/21/2019.
 */

public class TodoDetail {
    private int id;
    private  int titleId;
    private  String content;
//    private  boolean completed;

    public TodoDetail(int id, int titleId, String content)
    {
        this.id = id;
        this.titleId = titleId;
        this.content = content;


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTitleId() {
        return titleId;
    }

    public void setTitleId(int receiptId) {
        this.titleId = titleId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String description) {
        this.content = description;
    }

//    public boolean getComleted() {
//        return completed;
//    }
//
//    public void setCompleted(boolean price) {
//        this.completed = completed;
//    }
//}
}
