package com.example.service;

import com.example.DTO.Comment;
import com.example.entity.Event;
import com.example.entity.Person;
import com.example.exception.CustomerException;
import com.example.mapper.EventMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Resource
    private EventMapper eventMapper;

    public List<Event> selectAll(){
        List<Event> events = eventMapper.selectAll();
        if(events.isEmpty()){
            throw new CustomerException("未找到数据项");
        }
        return events;
    }

    public Event selectById(int id) {
        Event event =  eventMapper.selectById(id);
        if(event != null){
            return event;
        }
        else{
            throw new CustomerException("未找到数据项");
        }
    }

    public Event selectByName(String name) {
        return selectById(selectIdByName(name));
    }

    public Integer selectIdByName(String name){
        Integer id = eventMapper.selectIdByName(name);
        if(id != null){
            return id;
        }
        else {
            throw new CustomerException("未找到数据项");
        }
    }

    public String selectNameById(int id){
        String name  = eventMapper.selectNameById(id);
        if(name != null){
            return name;
        }
        else {
            throw new CustomerException("未找到数据项");
        }
    }

    public String selectIntroductionById(int id){
        String introduction = eventMapper.selectIntroductionById(id);
        if(introduction != null){
            return introduction;
        }
        else {
            throw new CustomerException("未找到数据项");
        }
    }

    public String selectIntroductionByName(String name){
        return selectIntroductionById(selectIdByName(name));
    }

    public String selectDetailById(int id) {
        String detail = eventMapper.selectDetailById(id);
        if(detail != null){
            return detail;
        }
        else {
            throw new CustomerException("未找到数据项");
        }
    }

    public String selectDetailByName(String name){
        return selectDetailById(selectIdByName(name));
    }

    public Person selectPersonById(int id){
        Person person = eventMapper.selectPersonById(id);
        if(person == null){
            throw new CustomerException("对应id事件不存在或未绑定人物");
        }
        return person;
    }

    public Person selectPersonByName(String name){
        return selectPersonById(selectIdByName(name));
    }

    public void updateById(Event event){
        selectById(event.getId());
        eventMapper.updateById(event);
    }

    public List<Comment> selectCommentsById(Integer id){
        selectById(id);
        return eventMapper.selectCommentsById(id);
    }
}
