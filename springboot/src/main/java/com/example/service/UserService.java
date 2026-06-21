package com.example.service;

import com.example.DTO.Collections;
import com.example.DTO.Collection;
import com.example.DTO.Comment;
import com.example.entity.Event;
import com.example.entity.User;
import com.example.exception.CustomerException;
import com.example.mapper.EventMapper;
import com.example.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    @Resource
    UserMapper userMapper;

    @Resource
    EventMapper eventMapper;

    public Integer admin(User user){
        Integer id = userMapper.selectIdByName(user.getName());
        System.out.println(id);
        User a = userMapper.selectById(id);
        System.out.println(a);
        if(a == null){
            throw new CustomerException("用户名不存在");
        } else if (!a.getPassword().equals(user.getPassword())) {
            throw new CustomerException("密码错误");
        }
        return a.getId();
    }

    public List<User> selectAll(){
        return userMapper.selectAll();
    }

    public User selectById(int id){
        User user = userMapper.selectById(id);
        if(user == null){
            throw new CustomerException("未查询到用户id");
        }
        return user;
    }

    public User selectByName(String name){
        Integer id = userMapper.selectIdByName(name);
        if(id == null){
            throw new CustomerException("未查询到用户名1");
        }
        return userMapper.selectById(id);
    }

    public User selectByName2(String name){
        Integer id = userMapper.selectIdByName(name);

        return userMapper.selectById(id);
    }

    public void insert(User user){
        User a = selectByName2(user.getName());//查询库中是否存在该用户名
        if(a != null){
            throw new CustomerException("用户名已存在");
        }
        userMapper.insert(user);
    }

    public void updateById(User user){
        User a = userMapper.selectById(user.getId());
        if(a == null){
            throw new CustomerException("未查询到用户id");
        }
        userMapper.updateById(user);
    }

    public void deleteById(Integer id){
        User a = userMapper.selectById(id);
        if(a == null){
            throw new CustomerException("未查询到用户id");
        }
        userMapper.deleteCollectionById(id);
        userMapper.deleteCommentById(id);
        userMapper.deleteById(id);
    }

    public void deleteByName(String name){
        Integer id = userMapper.selectIdByName(name);
        if(id == null){
            throw new CustomerException("未查询到用户id");
        }
        userMapper.deleteCollectionById(id);
        userMapper.deleteCommentById(id);
        userMapper.deleteById(id);
    }

    public void collect(Collection collection){
        User user = userMapper.selectById(collection.getUser_id());
        if(user == null){
            throw new CustomerException("未查询到用户id");
        }
        Event event = eventMapper.selectById(collection.getEvent_id());
        if(event == null){
            throw new CustomerException("未查询到事件id");
        }
        userMapper.collectWithEvent(collection);
    }

    public void comment(Comment comment){
        User user = userMapper.selectById(comment.getUser_id());
        if(user == null){
            throw new CustomerException("未查询到用户id");
        }
        Event event = eventMapper.selectById(comment.getEvent_id());
        if(event == null){
            throw new CustomerException("未查询到事件id");
        }

        userMapper.commentWithEvent(comment);
    }

    public void deleteComment(Comment comment){
        userMapper.deleteComment(comment);
    }

    public void selectCollection(Collections collection){
            List<Collections> c = userMapper.selectCollection(collection);
            if(c.isEmpty()){
            throw new CustomerException("未收藏");
        }
    }

    public List<Integer> selectCollectionIdsById(Integer id){
        List<Integer> c = userMapper.selectCollectionIdsById(id);
        if(c.isEmpty()){
            throw new CustomerException("该用户未收藏事件 用户id"+id);
        }
        return c;
    }

    public List<Comment> selectCommentsById(Integer id){
        List<Comment> c = userMapper.selectCommentsById(id);
        if(c.isEmpty()){
            throw new CustomerException("该用户未发表评论 用户id"+id);
        }
        System.out.println(c);
        return c;
    }

    public void deleteCommentById(Integer id){
        userMapper.deleteCommentById(id);
    }

    public void deleteCollection(Collection collection){
        userMapper.deleteCollection(collection);
    }
}
