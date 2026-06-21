package com.example.DTO;

import com.example.exception.CustomerException;

public class Collection {
    private Integer user_id;
    private Integer event_id;

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

    public void if_lack(){
        if(user_id==null){
            throw new CustomerException("Collection:user_id字段缺失或类型不正确");
        }
        if(event_id==null){
            throw new CustomerException("Collection:event_id字段缺失或类型不正确");
        }
    }
}
