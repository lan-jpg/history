package com.example.DTO;

import com.example.exception.CustomerException;

public class Introduction {
    private String introduction;

    public Introduction(String introduction) {
        this.introduction = introduction;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void if_lack(){
        if(introduction == null){
            throw new CustomerException("Introduction:introduction字段缺失或类型不正确");
        }
    }
}
