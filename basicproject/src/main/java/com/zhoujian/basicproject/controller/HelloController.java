package com.zhoujian.basicproject.controller;

import com.zhoujian.basicproject.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HelloController {
    @Autowired
    private ISysUserService userService;

    @RequestMapping("/index")
    public String sayHello() {
        log.info("进来了");
        return "index";
    }

    @PostMapping("/user")
    public String selectUsername() {
        log.info("进来了");
        return userService.selectUserById(1l).toString();
    }


}

