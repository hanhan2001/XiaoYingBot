package me.xiaoying.bot.controller;

import me.xiaoying.bot.api.XiaoYing;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
//@RestController
public class IndexController {

//    @RequestMapping("/index")
    @GetMapping("/index")
    public String index() {
        return "/index/index.html";
    }

//    @RequestMapping("/close")
    public void closeSystem() {
        System.exit(0);
    }

//    @RequestMapping("/reload")
    public String reloadSystem() {
        XiaoYing.getServer().reload();
        return "Success";
    }
}