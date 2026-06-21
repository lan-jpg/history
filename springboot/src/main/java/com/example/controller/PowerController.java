package com.example.controller;

import com.example.DTO.Id;
import com.example.DTO.Introduction;
import com.example.DTO.Name;
import com.example.common.Result;
import com.example.service.PowerService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/power")
public class PowerController {

    @Resource
    PowerService powerService;

    @GetMapping("/selectAll")
    public Result selectAll() {
        System.out.println("接到查找所有政权请求:");
        return Result.success(powerService.selectAll());
    }

    @PostMapping("/selectNameById")
    public Result selectNameById(@RequestBody Id ID) {
        ID.if_lack();
        Integer id = ID.getId();
        System.out.println("接到查找政权name请求 id:"+id);
        Name name = new Name(powerService.selectNameById(id));
        return Result.success(name);
    }

    @PostMapping("/selectIdByName")
    public Result selectIdByName(@RequestBody Name NAME) {
        NAME.if_lack();
        String name = NAME.getName();
        System.out.println("接到查找政权id请求 name:"+name);
        Id id = new Id(powerService.selectIdByName(name));
        return Result.success(id);
    }

    @PostMapping("/selectById")
    public Result selectById(@RequestBody Id ID) {
        ID.if_lack();
        Integer id = ID.getId();
        System.out.println("接到查找政权请求 id:"+id);
        return Result.success(powerService.selectById(id));
    }

    @PostMapping("/selectByName")
    public Result selectByName(@RequestBody Name NAME) {
        NAME.if_lack();
        String name = NAME.getName();
        System.out.println("接到查找政权请求 name:"+name);
        return Result.success(powerService.selectByName(name));
    }

    @PostMapping("/selectIntroductionById")
    public Result selectIntroductionById(@RequestBody Id ID) {
        ID.if_lack();
        Integer id = ID.getId();
        System.out.println("接到查找政权介绍请求 id:"+id);
        Introduction introduction = new Introduction(powerService.selectIntroductionById(id));
        return Result.success(introduction);
    }

    @PostMapping("/selectIntroductionByName")
    public Result selectIntroductionByName(@RequestBody Name NAME) {
        NAME.if_lack();
        String name = NAME.getName();
        System.out.println("接到朝代查找介绍请求 name:"+name);
        Introduction introduction = new Introduction(powerService.selectIntroductionByName(name));
        return Result.success(introduction);
    }

    @PostMapping("/selectPersonsById")
    public Result selectPersonsById(@RequestBody Id ID) {
        ID.if_lack();
        Integer id = ID.getId();
        System.out.println("接到查找政权人物请求 id:"+id);
        return Result.success(powerService.selectPersonsById(id));
    }

    @PostMapping("/selectPersonsByName")
    public Result selectPersonsByName(@RequestBody Name NAME) {
        NAME.if_lack();
        String name = NAME.getName();
        System.out.println("接到查找朝代政权请求 name:"+name);
        return Result.success(powerService.selectPersonsByName(name));
    }

    @PostMapping("/selectDynastyById")
    public Result selectDynastyById(@RequestBody Id ID) {
        ID.if_lack();
        Integer id = ID.getId();
        System.out.println("接到查找政权朝代请求 id:"+id);
        return Result.success(powerService.selectDynastyById(id));
    }

    @PostMapping("/selectDynastyByName")
    public Result selectDynastyByName(@RequestBody Name NAME) {
        NAME.if_lack();
        String name = NAME.getName();
        System.out.println("接到查找政权朝代请求 name:"+name);
        return Result.success(powerService.selectDynastyByName(name));
    }
}
