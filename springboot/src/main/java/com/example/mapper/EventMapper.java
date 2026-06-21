
package com.example.mapper;

import com.example.DTO.Comment;
import com.example.entity.Event;
import com.example.entity.Person;

import java.util.List;

public interface EventMapper {

    List<Event> selectAll();

    Event selectById(Integer id);

    String selectNameById(Integer id);

    Integer selectIdByName(String name);

    String selectIntroductionById(Integer id);

    String selectDetailById(Integer id);

    Person selectPersonById(Integer id);

    void updateById(Event event);

    List<Comment> selectCommentsById(Integer id);
}
