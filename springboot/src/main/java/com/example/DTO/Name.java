package com.example.DTO;

import com.example.exception.CustomerException;

public class Name {

    private String name;

    public Name(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void if_lack(){
        if(name == null){
            throw new CustomerException("Name:name字段缺失或类型不正确");
        }
    }
}
