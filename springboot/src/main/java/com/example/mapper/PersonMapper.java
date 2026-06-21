package com.example.mapper;

import com.example.entity.Event;
import com.example.entity.Person;
import com.example.entity.Power;

import java.util.List;

public interface PersonMapper {

    List<Person> selectAll();

    String selectNameById(Integer id);

    Integer selectIdByName(String name);

    Person selectById(Integer id);

    String selectIntroductionById(Integer id);

    Power selectPowerById(Integer id);

    List<Event> selectEventsById(Integer id);

    String selectTimeById(Integer id);
}
