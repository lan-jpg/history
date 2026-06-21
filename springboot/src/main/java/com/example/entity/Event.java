package com.example.entity;

public class Event {
    private Integer id;
    private String name;
    private String introduction;
    private String detail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getDetails() {
        return detail;
    }

    public void setDetails(String detail) {
        this.detail = detail;
    }

    public void if_lack(){
        if(id == null){
            throw new NullPointerException("Event:id字段缺失或类型不正确");
        }
        if(name == null){
            throw new NullPointerException("Event:name字段缺失或类型不正确");
        }
        if(introduction == null){
            throw new NullPointerException("Event:introduction字段缺失或类型不正确");
        }
        if(detail == null){
            throw new NullPointerException("Event:detail字段缺失或类型不正确");
        }
    }

}
