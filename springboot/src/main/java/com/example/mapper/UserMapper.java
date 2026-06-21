package com.example.mapper;

import com.example.DTO.Collections;
import com.example.DTO.Collection;
import com.example.DTO.Comment;
import com.example.entity.User;

import java.util.List;

public interface UserMapper {

    List<User> selectAll();

    User selectById(Integer id);

    String selectNameById(String name);

    Integer selectIdByName(String name);

    void insert(User user);

    void deleteById(Integer id);

    void updateById(User user);

    void collectWithEvent(Collection collection);

    void commentWithEvent(Comment comment);

    void deleteCollectionById(Integer id);

    void deleteCommentById(Integer id);

    void deleteCollection(Collection collection);

    void deleteComment(Comment comment);

    List<Integer> selectCollectionIdsById(Integer id);

    List<Comment> selectCommentsById(Integer id);

    List<Collections> selectCollection(Collections collection);



}
