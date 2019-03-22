package com.example.anton.todoornot;

/**
 * Created by adrantiev1 on 3/12/2019.
 */

public class Todo {
    private int id;
    private String todoTitle = "";

    public Todo(int id, String todoTitle){
        this.id = id;
        this.todoTitle =todoTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTodoTitle()
    {
        return todoTitle;
    }
    public void setTodoTitle(String todoTitle)
    {
        this.todoTitle = todoTitle;
    }
}
