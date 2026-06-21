package com.example.service;

import com.example.entity.Event;
import com.example.entity.Person;
import com.example.entity.Power;
import com.example.exception.CustomerException;
import com.example.mapper.PersonMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Resource
    PersonMapper personMapper;

    public List<Person> selectAll(){
        List<Person> events = personMapper.selectAll();
        if(events.isEmpty()){
            throw new CustomerException("未找到人物数据");
        }
        return events;
    }

    public Person selectById(Integer id) {
        Person person = personMapper.selectById(id);
        if(person != null){
            return person;
        }
        else{
            throw new CustomerException("未找到对应id的人物");
        }
    }

    public Person selectByName(String name) {
        return selectById(selectIdByName(name));
    }

    public Integer selectIdByName(String name){
        Integer id = personMapper.selectIdByName(name);
        if(id != null){
            return id;
        }
        else {
            throw new CustomerException("未找到对应name的人物");
        }
    }

    public String selectNameById(int id){
        String name  = personMapper.selectNameById(id);
        if(name != null){
            return name;
        }
        else {
            throw new CustomerException("未找到对应id的人物");
        }
    }

    public String selectIntroductionById(int id){
        String introduction = personMapper.selectIntroductionById(id);
        if(introduction != null){
            return introduction;
        }
        else {
            throw new CustomerException("未找到对应id的人物或未找到简介");
        }
    }

    public String selectIntroductionByName(String name){
        return selectIntroductionById(selectIdByName(name));
    }

    public Power selectPowerById(int id) {
        Power power = personMapper.selectPowerById(id);
        if(power != null){
            return power;
        }
        else {
            throw new CustomerException("未找到对于id的人物或未绑定政权");
        }
    }

    public Power selectPowerByName(String name){
        return selectPowerById(selectIdByName(name));
    }

    public List<Event> selectEventsById(int id){
        List<Event> events= personMapper.selectEventsById(id);
        if(events.isEmpty()){
            throw new CustomerException("未找到对应id人物或该人物无对应事件");
        }
        return events;
    }

    public List<Event> selectEventsByName(String name){
        return selectEventsById(selectIdByName(name));
    }

    public String selectTimeById(Integer id){
        Person person = selectById(id);
        return personMapper.selectTimeById(id);
    }
}
