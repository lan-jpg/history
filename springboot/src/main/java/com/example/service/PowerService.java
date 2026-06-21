package com.example.service;

import com.example.entity.Dynasty;
import com.example.entity.Person;
import com.example.entity.Power;
import com.example.exception.CustomerException;
import com.example.mapper.PowerMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PowerService {

    @Resource
    PowerMapper powerMapper;

    public List<Power> selectAll() {
        return powerMapper.selectAll();
    }

    public Integer selectIdByName(String name){
        Integer id = powerMapper.selectIdByName(name);
        if(id == null){
            throw new CustomerException("未找到到对应name的政权");
        }
        return id;
    }

    public String selectNameById(Integer id){
        String name = powerMapper.selectNameById(id);
        if(name == null){
            throw new CustomerException("未找到到对应id的政权");
        }
        return name;
    }

    public Power selectById(int id){
        Power power = powerMapper.selectById(id);
        if(power == null){
            throw new CustomerException("未找到到对应id的政权");
        }
        return power;
    }

    public Power selectByName(String name){
        return powerMapper.selectById(selectIdByName(name));
    }

    public String selectIntroductionById(int id){
        String introduction = powerMapper.selectIntroductionById(id);
        if(introduction == null){
            throw new CustomerException("未查询到对应政权的介绍");
        }
        return introduction;
    }

    public String selectIntroductionByName(String name){
        return selectIntroductionById(selectIdByName(name));
    }

    public Dynasty selectDynastyById(int id){
        Dynasty dynasty = powerMapper.selectDynastyById(id);
        if(dynasty == null){
            throw new CustomerException("未查询到对应政权的朝代");
        }
        return dynasty;
    }

    public Dynasty selectDynastyByName(String name){
        return selectDynastyById(selectIdByName(name));
    }

    public List<Person> selectPersonsById(int id){
        List<Person> persons = powerMapper.selectPersonsById(id);
        if(persons == null){
            throw new CustomerException("未查询到对应政权的历史人物或朝代id错误");
        }
        return persons;
    }

    public List<Person> selectPersonsByName(String name){
        return selectPersonsById(selectIdByName(name));
    }

}
