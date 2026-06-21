package com.example.DTO;

import com.example.exception.CustomerException;

public class EventPerson {
    private Integer person_id;
    private Integer event_id;

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Integer person_id) {
        this.person_id = person_id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(Integer event_id) {
        this.event_id = event_id;
    }

    public void if_lack(){
        if(person_id == null){
            throw new CustomerException("EventPerson:person_id字段缺失或类型不正确");
        }
        if(event_id == null){
            throw new CustomerException("EventPerson:event_id字段缺失或类型不正确");
        }
    }
}
