package com.example.adrantiev1.chatter;

/**
 * Created by adrantiev1 on 1/30/2019.
 * Chat class with 3 properties Date, Sender, Content
 */

public class Chat
{
    private String chatDate = "";
    private String chatSender = "";
    private String chatContent   = "";

    public String getChatDate() {
        return chatDate;
    }

    public void setChatDate(String chatDate) {
        this.chatDate = chatDate;
    }

    public String getChatSender() {
        return chatSender;
    }

    public void setChatSender(String chatSender) {
        this.chatSender = chatSender;
    }

    public String getChatContent() {
        return chatContent;
    }

    public void setChatContent(String chatContent) {
        this.chatContent = chatContent;
    }
}
