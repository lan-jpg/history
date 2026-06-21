package com.example.mapper;

import com.example.entity.Dynasty;
import com.example.entity.Person;
import com.example.entity.Power;

import java.util.List;

public interface PowerMapper {

    List<Power> selectAll();

    String selectNameById(Integer id);

    Integer selectIdByName(String name);

    Power selectById(Integer id);

    String selectIntroductionById(Integer id);

    Dynasty selectDynastyById(Integer id);

    List<Person> selectPersonsById(Integer id);

}
