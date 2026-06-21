package com.example.mapper;

import com.example.entity.Dynasty;
import com.example.entity.Power;

import java.util.List;

public interface DynastyMapper {

    List<Dynasty> selectAll();

    Dynasty selectById(Integer id);

    Integer selectIdByName(String name);

    String selectNameById(Integer id);

    String selectIntroductionById(Integer id);

    List<Power> selectPowersById(Integer id);

}
