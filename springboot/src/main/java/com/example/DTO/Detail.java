package com.example.DTO;

import com.example.exception.CustomerException;

public class Detail {
    private String detail;

    public Detail(String detail) {
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void if_lack(){
        if(detail == null){
            throw new CustomerException("Detail:detail字段缺失或类型不正确");
        }
    }
}
