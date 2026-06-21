package com.example.DTO;

import com.example.exception.CustomerException;

public class Comment {
    private Integer id;
    private Integer user_id;
    private Integer event_id;
    private String content;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {return id;}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", user_id=" + user_id   +
                ", event_id="+event_id +
                ", content=" + content +
                '}';
    }

    public void if_lack(){
        if(id == null){
            throw new CustomerException("Comment:id字段缺失或类型不正确");
        }
        if(user_id == null){
            throw new CustomerException("Comment:user_id字段缺失或类型不正确");
        }
        if(event_id == null){
            throw new CustomerException("Comment:event_id字段缺失或类型不正确");
        }
        if(content == null){
            throw new CustomerException("Comment:comment字段缺失或类型不正确");
        }
    }
}
