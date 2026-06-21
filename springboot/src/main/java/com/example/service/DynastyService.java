package com.example.service;

import com.example.entity.Dynasty;
import com.example.entity.Power;
import com.example.exception.CustomerException;
import com.example.mapper.DynastyMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DynastyService {

    @Resource
    DynastyMapper dynastyMapper;

    public List<Dynasty> selectAll() {
        return dynastyMapper.selectAll();
    }

    public Integer selectIdByName(String name){
        Integer id = dynastyMapper.selectIdByName(name);
        if(id == null){
            throw new CustomerException("未找到到对应name的朝代");
        }
        return id;
    }

    public String selectNameById(Integer id){
        String name = dynastyMapper.selectNameById(id);
        if(name == null){
            throw new CustomerException("未找到到对应id的朝代");
        }
        return name;
    }

    public Dynasty selectById(int id){
        Dynasty dynasty = dynastyMapper.selectById(id);
        if(dynasty == null){
            throw new CustomerException("未找到到对应id的朝代");
        }
        return dynasty;
    }

    public Dynasty selectByName(String name){
        return dynastyMapper.selectById(selectIdByName(name));
    }

    public String selectIntroductionById(int id){
        String introduction = dynastyMapper.selectIntroductionById(id);
        if(introduction == null){
            throw new CustomerException("未查询到对应朝代的介绍");
        }
        return introduction;
    }

    public String selectIntroductionByName(String name){
        return selectIntroductionById(selectIdByName(name));
    }

    public List<Power> selectPowersById(int id){
        List<Power> powers = dynastyMapper.selectPowersById(id);
        if(powers == null){
            throw new CustomerException("未查询到对应朝代的政权或朝代id错误");
        }
        return powers;
    }

    public List<Power> selectPowersByName(String name){
        return selectPowersById(selectIdByName(name));
    }
}
