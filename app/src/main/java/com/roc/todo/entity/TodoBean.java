package com.roc.todo.entity;

/**
 * Created by 赖鹏旭 on 2018/3/14.
 * 待办事项实体类
 */
public class TodoBean {
    private int id;
    private String todoContent;

    public TodoBean(int id, String todoContent) {
        this.id = id;
        this.todoContent = todoContent;
    }

    public int getId() {
        return id;
    }

    public String getTodoContent() {
        return todoContent;
    }

}
