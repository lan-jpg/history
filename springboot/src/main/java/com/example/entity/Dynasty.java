package com.example.entity;

import com.example.exception.CustomerException;

public class Dynasty {
    private Integer id;
    private String name;
    private String introduction;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void if_lack(){
        if(id == null){
            throw new CustomerException("Dynasty: id缺失或类型不正确");
        }
        if(name == null){
            throw new CustomerException("Dynasty: name缺失或类型不正确");
        }
        if(introduction == null){
            throw new CustomerException("Dynasty: introduction缺失或类型不正确");
        }
    }
}
