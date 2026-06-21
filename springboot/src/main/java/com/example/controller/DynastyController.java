package com.example.controller;

import com.example.DTO.Id;
import com.example.DTO.Introduction;
import com.example.DTO.Name;
import com.example.common.Result;
import com.example.service.DynastyService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dynasty")
public class DynastyController {

    @Resource
    DynastyService dynastyService;

    @GetMapping("/selectAll")
    public Result selectAll() {
        System.out.println("接到查找所有朝代请求:");
        return Result.success(dynastyService.selectAll());
    }

    @PostMapping("/selectNameById")
    public Result selectNameById(@RequestBody Id ID) {
        ID.if_lack();
        Integer id = ID.getId();
        System.out.println("接到朝代查找请求 id:"+id);
        Name name = new Name(dynastyService.selectNameById(id));
        return Result.success(name);
    }

    @PostMapping("/selectIdByName")
    public Result selectIdByName(@RequestBody Name NAME) {
        NAME.if_lack();
        String name = NAME.getName();
        System.out.println("接到朝代查找请求 name:"+name);
        Id id = new Id(dynastyService.selectIdByName(name));
        return Result.success(id);
    }

    @PostMapping("/selectById")
    public Result selectById(@RequestBody Id ID) {
        ID.if_lack();
        Integer id = ID.getId();
        System.out.println("接到朝代查找请求 id:"+id);
        return Result.success(dynastyService.selectById(id));
    }

    @PostMapping("/selectByName")
    public Result selectByName(@RequestBody Name NAME) {
        NAME.if_lack();
        String name = NAME.getName();
        System.out.println("接到朝代查找请求 name:"+name);
        return Result.success( dynastyService.selectByName(name));
    }

    @PostMapping("/selectIntroductionById")
    public Result selectIntroductionById(@RequestBody Id ID) {
        ID.if_lack();
        Integer id = ID.getId();
        System.out.println("接到朝代查找介绍请求 id:"+id);
        Introduction introduction = new Introduction(dynastyService.selectIntroductionById(id));
        return Result.success(introduction);
    }

    @PostMapping("/selectIntroductionByName")
    public Result selectIntroductionByName(@RequestBody Name NAME) {
        NAME.if_lack();
        String name = NAME.getName();
        System.out.println("接到朝代查找介绍请求 name:"+name);
        Introduction introduction = new Introduction(dynastyService.selectIntroductionByName(name));
        return Result.success(introduction);
    }

    @PostMapping("/selectPowersById")
    public Result selectPowersById(@RequestBody Id ID) {
        ID.if_lack();
        Integer id = ID.getId();
        System.out.println("接到查找朝代政权请求 id:"+id);
        return Result.success(dynastyService.selectPowersById(id));
    }

    @PostMapping("/selectPowersByName")
    public Result selectPowersByName(@RequestBody Name NAME) {
        NAME.if_lack();
        String name = NAME.getName();
        System.out.println("接到查找朝代政权请求 name:"+name);
        return Result.success(dynastyService.selectPowersByName(name));
    }

}
