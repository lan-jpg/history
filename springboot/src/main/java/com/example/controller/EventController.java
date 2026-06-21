package com.example.controller;

import com.example.DTO.Detail;
import com.example.DTO.Id;
import com.example.DTO.Introduction;
import com.example.DTO.Name;
import com.example.common.Result;
import com.example.entity.Event;
import com.example.entity.Person;
import com.example.service.EventService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {

    @Resource
    EventService eventService;

    @GetMapping("/selectAll")
    public Result selectAll() {
        System.out.println("收到查询全部事件请求");
        List<Event> events = eventService.selectAll();
        return Result.success(events);
    }

    @PostMapping("/selectById")
    public Result selectById(@RequestBody Id ID) {
        ID.if_lack();
        Integer id = ID.getId();
        System.out.println("接到查询事件请求 id:"+id);
        Event event = eventService.selectById(id);
        return Result.success(event);
    }

    @PostMapping("/selectByName")
    public Result selectById(@RequestBody Name NAME) {
        NAME.if_lack();
        String name = NAME.getName();
        System.out.println("接到查询事件请求 name:"+name);
        Event event = eventService.selectByName(name);
        return Result.success(event);
    }

    @PostMapping("/selectIdByName")
    public Result selectIdByName(@RequestBody Name NAME) {
        NAME.if_lack();
        String name = NAME.getName();
        System.out.println("接到查询事件id请求 name:"+name);
        Id id = new Id(eventService.selectIdByName(name));
        return Result.success(id);
    }

    @PostMapping("/selectNameById")
    public Result selectNameById(@RequestBody Id ID) {
        ID.if_lack();
        Integer id = ID.getId();
        System.out.println("接到查询事件名请求 id:"+id);
        Name name = new Name(eventService.selectNameById(id));
        return Result.success(name);
    }

    @PostMapping("/selectIntroductionById")
    public Result selectIntroductionById(@RequestBody Id ID) {
        ID.if_lack();
        Integer id = ID.getId();
        System.out.println("接到查询事件简介请求 id:"+id);
        Introduction introduction = new Introduction(eventService.selectIntroductionById(id));
        return Result.success(introduction);
    }

    @PostMapping("/selectIntroductionByName")
    public Result selectIntroductionByName(@RequestBody Name NAME) {
        NAME.if_lack();
        String name = NAME.getName();
        System.out.println("接到查询事件简介请求 name:"+name);
        Introduction introduction = new Introduction(eventService.selectIntroductionByName(name));
        return Result.success(introduction);
    }

    @PostMapping("/selectDetailById")
    public Result selectDetailById(@RequestBody Id ID) {
        ID.if_lack();
        Integer id = ID.getId();
        System.out.println("接到查询事件详情请求 id:"+id);
        Detail detail = new Detail(eventService.selectDetailById(id));
        return Result.success(detail);
    }

    @PostMapping("/selectDetailByName")
    public Result selectDetailByName(@RequestBody Name NAME) {
        NAME.if_lack();
        String name = NAME.getName();
        System.out.println("接到查询事件详情请求 name:"+name);
        Detail detail = new Detail(eventService.selectDetailByName(name));
        return Result.success(detail);
    }

    @PostMapping("selectPersonById")
    public Result selectPersonById(@RequestBody Id ID) {
        ID.if_lack();
        Integer id = ID.getId();
        System.out.println("接到查询事件对应人物请求 事件id:"+id);
        Person person = eventService.selectPersonById(id);
        return Result.success(person);
    }


    @PostMapping("selectPersonByName")
    public Result selectPersonByName(@RequestBody Name NAME) {
        NAME.if_lack();
        String name = NAME.getName();
        System.out.println("接到查询事件对应人物请求 事件name:"+name);
        Person person = eventService.selectPersonByName(name);
        return Result.success(person);
    }

    @PostMapping("updateById")
    public Result updateById(@RequestBody Event event) {
        event.if_lack();
        System.out.println("接到事件修改请求 id:"+event.getId()+" name:"+event.getName());
        eventService.updateById(event);
        return Result.success();
    }

    @PostMapping("selectCommentsById")
    public Result selectCommentsById(@RequestBody Id ID){
        ID.if_lack();
        Integer id = ID.getId();
        System.out.println("接到查询事件评论请求 事件id:"+id);
        return Result.success(eventService.selectCommentsById(id));
    }
}
