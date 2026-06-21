package com.example.entity;

import com.example.exception.CustomerException;

public class User {
    private Integer id;
    private String name;
    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void if_lack(){
        if(id==null){
            throw new CustomerException("User:id字段缺失或格式不正确");
        }
        if(name==null){
            throw new CustomerException("User:name字段缺失或格式不正确");
        }
        if(password==null){
            throw new CustomerException("User:password字段缺失或格式不正确");
        }
    }

    public String toString() {
        return "User" +
                "id=" + id +
                ", name=" + name   +
                ", password="+password +
                '}';
    }
}
