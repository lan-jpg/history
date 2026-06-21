package com.example.controller;

import com.example.DTO.Id;
import com.example.DTO.Introduction;
import com.example.DTO.Name;
import com.example.DTO.Time;
import com.example.common.Result;
import com.example.entity.Event;
import com.example.entity.Person;
import com.example.entity.Power;
import com.example.service.PersonService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Resource
    PersonService personService;

    @GetMapping("/selectAll")
    public Result selectAll() {
        System.out.println("收到查询全部人物请求");
        List<Person> persons = personService.selectAll();
        return Result.success(persons);
    }

    @PostMapping("/selectById")
    public Result selectById(@RequestBody Id ID) {
        ID.if_lack();
        Integer id = ID.getId();
        System.out.println("接到查询人物请求 id:"+id);
        Person person = personService.selectById(id);
        return Result.success(person);
    }

    @PostMapping("/selectByName")
    public Result selectById(@RequestBody Name NAME) {
        NAME.if_lack();
        String name = NAME.getName();
        System.out.println("接到查询人物请求 name:"+name);
        Person person = personService.selectByName(name);
        return Result.success(person);
    }

    @PostMapping("/selectIdByName")
    public Result selectIdByName(@RequestBody Name NAME) {
        NAME.if_lack();
        String name = NAME.getName();
        System.out.println("接到查询人物id请求 name:"+name);
        Id id = new Id(personService.selectIdByName(name));
        return Result.success(id);
    }

    @PostMapping("/selectNameById")
    public Result selectNameById(@RequestBody Id ID) {
        ID.if_lack();
        Integer id = ID.getId();
        System.out.println("接到查询人物名请求 id:"+id);
        Name name = new Name(personService.selectNameById(id));
        return Result.success(name);
    }

    @PostMapping("/selectIntroductionById")
    public Result selectIntroductionById(@RequestBody Id ID) {
        ID.if_lack();
        Integer id = ID.getId();
        System.out.println("接到查询人物简介请求 id:"+id);
        Introduction introduction = new Introduction(personService.selectIntroductionById(id));
        return Result.success(introduction);
    }

    @PostMapping("/selectIntroductionByName")
    public Result selectIntroductionByName(@RequestBody Name NAME) {
        NAME.if_lack();
        String name = NAME.getName();
        System.out.println("接到查询人物简介请求 name:"+name);
        Introduction introduction = new Introduction(personService.selectIntroductionByName(name));
        return Result.success(introduction);
    }

    @PostMapping("/selectPowerById")
    public Result selectDetailById(@RequestBody Id ID) {
        ID.if_lack();
        Integer id = ID.getId();
        System.out.println("接到查询人物政权请求 id:"+id);
        Power power = personService.selectPowerById(id);
        return Result.success(power);
    }

    @PostMapping("/selectPowerByName")
    public Result selectDetailByName(@RequestBody Name NAME) {
        NAME.if_lack();
        String name = NAME.getName();
        System.out.println("接到查询人物政权请求 name:"+name);
        Power power = personService.selectPowerByName(name);
        return Result.success(power);
    }

    @PostMapping("selectEventsById")
    public Result selectEventsById(@RequestBody Id ID) {
        ID.if_lack();
        Integer id = ID.getId();
        System.out.println("接到查询人物对于事件请求 人物id:"+id);
        List<Event> events = personService.selectEventsById(id);
        return Result.success(events);
    }

    @PostMapping("selectEventsByName")
    public Result selectEventsByName(@RequestBody Name NAME) {
        NAME.if_lack();
        String name = NAME.getName();
        System.out.println("接到查询人物对应事件请求 人物name:"+name);
        List<Event> events = personService.selectEventsByName(name);
        return Result.success(events);
    }

    @PostMapping("selectTimeById")
    public Result selectTimeById(@RequestBody Id ID) {
        ID.if_lack();
        Integer id = ID.getId();
        System.out.println("接到查询人物在位时间请求 id:"+id);
        Time time = new Time(personService.selectTimeById(id));
        System.out.println("查询到的时间为:"+time.getTime());
        return Result.success(time);
    }
}
