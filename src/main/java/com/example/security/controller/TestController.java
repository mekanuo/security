package com.example.security.controller;

import com.alibaba.fastjson.JSON;
import com.example.security.entity.Users;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TestController {

    @GetMapping("hello")
    @ResponseBody
    public String hello(){
        return "hello security";
    }

    @GetMapping("userLogin")
    public String userLogin(){
        return "login/login";
    }

    @GetMapping("/login/success")
    public String success(){
        return "success";
    }

    @GetMapping("index")
    @ResponseBody
    public String index(){
        return "Hello,This is index";
    }

    /**
     * 开启 @EnableGlobalMethodSecurity(securedEnabled = true)
     * 方法级别 或者 类级别上需要 使用 @Secured标签填写需要校验的角色，
     * 需要注意前缀"ROLE_"
     * @return
     */
    @Secured({"ROLE_12","ROLE_34"})
    @GetMapping("secured")
    @ResponseBody
    public String secured(){
        return "hello,this is secured";
    }


//    @PreAuthorize("hasAuthority(@methodSecurity.hasAuthority())")
//    @PreAuthorize("hasAnyAuthority(@methodSecurity.hasAnyAuthority())")
//    @PreAuthorize("hasRole(@methodSecurity.hasRole())")
    @PreAuthorize("hasAnyRole(@methodSecurity.hasAnyRole())")
    @GetMapping("perAuth")
    @ResponseBody
    public String perAuth(){
        return "hello,This is perAuth";
    }


    @PostAuthorize("hasAuthority(@methodSecurity.hasAuthority())")
//    @PostAuthorize("hasAnyAuthority(@methodSecurity.hasAnyAuthority())")
//    @PostAuthorize("hasRole(@methodSecurity.hasRole())")
//    @PostAuthorize("hasAnyRole(@methodSecurity.hasAnyRole())")
    @GetMapping("postAuthorize")
    @ResponseBody
    public String postAuthorize(){
        System.out.println("hello postAuthorize");
        return "hello,This is postAuthorize";
    }

    /**
     * 测试postFilter标签对返回值过滤
     * @return
     */
    @PostAuthorize("hasAuthority('admin')")
    @GetMapping("postFilter")
    @PostFilter("filterObject.name=='admin1'")
    @ResponseBody
    public List<Users> postFilter(){
        List<Users> users = new ArrayList<Users>() {{
            this.add(new Users(1L, "admin1", "admin1"));
            this.add(new Users(2L, "admin2", "admin2"));
            this.add(new Users(3L, "admin3", "admin3"));
        }};
        return users;
    }


    /**
     * 测试PreFilter标签对入参过滤
     * @return
     */
    @PostMapping("preFilter")
    @PreFilter("filterObject.name=='admin1'")
    @ResponseBody
    public String preFilter(@RequestBody List<Users> list){
        list.forEach(po-> System.out.println(JSON.toJSONString(po)));
        return "SUCCESS";
    }



}