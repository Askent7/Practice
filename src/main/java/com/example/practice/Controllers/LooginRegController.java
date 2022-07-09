package com.example.practice.Controllers;


import com.example.practice.Models.Role;
import com.example.practice.Models.Useru;
import com.example.practice.Repos.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Map;

@Controller
public class LooginRegController {

    @Autowired
    private UserRepos userRepos;


    @GetMapping("/registration")
    public String registration(){

        return "registration";
    }

    @PostMapping("/registration")
    public String add_user( Useru user,
                            @RequestParam(value = "checkbox", required = false) String checkbox,
                            Map<String, Object> model ){

        Useru userfromDB = userRepos.findByUsername(user.getUsername());
        if(userfromDB!= null){
            model.put("message", "Такой пользователь уже зарегестрирован");
            return "registration";
        }

        boolean boll = false;
        if(checkbox == null){
            boll = true;
        }

        user.setActive(true);
        if(boll){
            user.setRoles(Collections.singleton(Role.ADMIN));
            System.out.println(1);
        }
        else
            user.setRoles(Collections.singleton(Role.USER));


        userRepos.save(user);
        return"redirect:/login";
    }
}
