package com.renjie.controller;

import com.renjie.entity.Users;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Fan
 * @Date 2020/11/16
 * @Description:
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    public String test(){
        return "hello,Security";
    }

    @GetMapping("/index")
    public String index(){
        return "hello index";
    }

    @GetMapping("/update")
//    @Secured("ROLE_admin")
//    @PreAuthorize("hasAnyAuthority('ROLE_admin,ROLE_user')")
    @PostAuthorize("hasAnyAuthority('ROLE_admin,user')")
    public String update(){
        System.out.println("update......");
        return "success to update";
    }

    @GetMapping("/getAll")
    @PostFilter("filterObject.username == 'Jack'")
    @PostAuthorize("hasAnyAuthority('ROLE_admin,ROLE_user')")
    public List<Users> getAllUser(){

        ArrayList<Users> list = new ArrayList<>();
        list.add(new Users(11,"Jack","123456","admin"));
        list.add(new Users(21,"Mary","123456","user"));
        System.out.println(list);
        return list;
    }




}
