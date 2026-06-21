package com.example.controller;

import com.example.DTO.Collection;
import com.example.DTO.Collections;
import com.example.DTO.Comment;
import com.example.DTO.Id;
import com.example.DTO.Name;
import com.example.common.Result;
import com.example.entity.User;
import com.example.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @GetMapping("/selectAll") // 完整的请求路径: http://ip:port/user/selectAll
    public Result selectAll() {
        System.out.println("收到用户全部查询请求");
        List<User> users = userService.selectAll();
        return Result.success(users);
    }

    @PostMapping("/selectById")
    public Result selectById(@RequestBody Id ID) {
        ID.if_lack();
        Integer id = ID.getId();
        System.out.println("收到用户查询请求 id: " + id);
        User user = userService.selectById(id);
        return Result.success(user);
    }

    @PostMapping("/selectByName")
    public Result selectByName(@RequestBody Name NAME) {
        NAME.if_lack();
        String name = NAME.getName();
        System.out.println("收到用户查询请求 name: " + name);
        User user = userService.selectByName(name);
        return Result.success(user);
    }

    @PostMapping("/admin")
    public Result admin(@RequestBody User user) {
        user.setId(0);
        user.if_lack();
        System.out.println("收到登录请求 用户信息: " + user);
        Id id = new Id(userService.admin(user));
        return Result.success(id);
    }

    @PostMapping("/insert") // 完整的请求路径: http://ip:port/user/insert
    public Result createUser(@RequestBody User user) {
        user.setId(0);
        user.if_lack();
        System.out.println("收到用户注册请求 注册信息: " + user);
        userService.insert(user);
        return Result.success();
    }

    @PostMapping("/updateById")
    public Result updateById(@RequestBody User user) {
        user.if_lack();
        System.out.println("收到更改账号信息请求 用户 " + user );
        userService.updateById(user);
        return Result.success();
    }

    @PostMapping("/deleteById")
    public Result deleteById(@RequestBody Id ID) {
        ID.if_lack();
        Integer id = ID.getId();
        System.out.println("收到用户删除请求 id: " + id);
        userService.deleteById(id);
        return Result.success();
    }

    @PostMapping("/deleteByName")
    public Result deleteByName(@RequestBody Name NAME) {
        NAME.if_lack();
        String name = NAME.getName();
        System.out.println("收到用户删除请求 name: " + name);
        userService.deleteByName(name);
        return Result.success();
    }

    @PostMapping("/collect")
    public Result collect(@RequestBody Collection collection) {
        collection.if_lack();
        System.out.println("收到用户收藏请求 请求体:" + collection);
        userService.collect(collection);
        return Result.success();
    }

    @PostMapping("/selectCollect")
    public Result selectCollect(@RequestBody Collections collection){
        collection.if_lack();
        System.out.println("收到用户询问是否收藏 请求体:" + collection);
        userService.selectCollection(collection);
        return Result.success();
    }

    @PostMapping("/selectCollectIdsById")
    public Result selectCollection(@RequestBody Id ID){
        ID.if_lack();
        Integer id = ID.getId();
        System.out.println("收到查询用户收藏关系请求 用户id:" + id);
        List<Integer> ids = userService.selectCollectionIdsById(id);
        return Result.success(ids);
    }

    @PostMapping("/selectCommentsById")
    public Result selectCommentsById(@RequestBody Id ID){

        ID.if_lack();
        Integer id = ID.getId();
        System.out.println("收到查询用户评论关系请求 用户id:" + id);
        List<Comment> comments = userService.selectCommentsById(id);
        System.out.println(comments);
        return Result.success(comments);
    }

    @PostMapping("/comment")
    public Result comment(@RequestBody Comment comment) {
        comment.setId(0);
        comment.if_lack();
        System.out.println("收到用户发布评论请求 请求体:" + comment);
        userService.comment(comment);
        return Result.success();
    }

    @PostMapping("/deleteCollection")
    public Result deleteCollection(@RequestBody Collection collection) {
        collection.if_lack();
        System.out.println("收到删除收藏请求 请求体:" + collection);
        userService.deleteCollection(collection);
        return Result.success();
    }

    @PostMapping("/deleteCommentById")
    public Result deleteCommentById(@RequestBody Id ID) {
        ID.if_lack();
        Integer id = ID.getId();
        System.out.println("收到删除评论请求 id:" + id);
        userService.deleteCommentById(id);
        return Result.success();
    }
}
