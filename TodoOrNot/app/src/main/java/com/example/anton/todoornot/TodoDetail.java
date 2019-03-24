package com.example.anton.todoornot;

/**
 * Created by anton on 3/21/2019.
 */

public class TodoDetail {
    private int id;
    private int titleId;
    private String content;
    private String dateCreated;
    private String completeFlag;

    public TodoDetail(int id, int titleId, String content,String dateCreated, String completeFlag) {
        this.id = id;
        this.titleId = titleId;
        this.content = content;
        this.dateCreated = dateCreated;
        this.completeFlag = completeFlag;


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


    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getCompleteFlag() {
        return completeFlag;
    }

    public void setCompleteFlag(String completeFlag) {
        this.completeFlag = completeFlag;
    }

}
