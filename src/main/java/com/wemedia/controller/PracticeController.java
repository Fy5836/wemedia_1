package com.wemedia.controller;

import com.wemedia.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PracticeController {
    @RequestMapping("/practice")
    public String pratice(ModelMap map){
        map.addAttribute("mao","Thymeleaf赋值start");
        map.addAttribute("maoName","老毛很帅");
        map.addAttribute("laomao","laomao");

        List<User> userList = new ArrayList<>();
        User user = new User();
        user.setUserId("610");
        user.setUsername("老毛");
        user.setPassword("11111");
        user.setSex(1);
        user.setAge(20);
        userList.add(user);
        map.addAttribute("userList",userList);
        map.addAttribute("url","group");
        map.addAttribute("number",10);
        map.addAttribute("switch","0");
        return "theme/laomao/practice";
    }
}
