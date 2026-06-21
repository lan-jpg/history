package com.example.DTO;

import com.example.exception.CustomerException;

public class Id {
    private Integer id;

    public Id(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void if_lack(){
        if(id == null){
            throw new CustomerException("Id:id字段缺失或类型不正确");
        }
    }
}
