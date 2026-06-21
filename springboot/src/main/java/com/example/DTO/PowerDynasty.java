package com.example.DTO;

public class PowerDynasty {
    private int person_id;
    private int dynasty_id;
    private int position;
    private String time;

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public int getDynasty_id() {
        return dynasty_id;
    }

    public void setDynasty_id(int dynasty_id) {
        this.dynasty_id = dynasty_id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
