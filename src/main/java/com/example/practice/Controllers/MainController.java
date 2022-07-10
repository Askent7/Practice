package com.example.practice.Controllers;

import com.example.practice.Models.Role;
import com.example.practice.Models.Useru;
import com.example.practice.Repos.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

import static com.example.practice.Models.Role.ADMIN;

@Controller
public class MainController {

    @Autowired
    private UserRepos userRepos;

    @GetMapping("/")
    public String home( Model model,
                        @AuthenticationPrincipal User user_cur2) {

        model.addAttribute("title", "Главная страница");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getPrincipal() != "anonymousUser"){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(1);
        System.out.println(user.getUsername());
        Useru user_cur = userRepos.findByUsername(user.getUsername());
        Boolean bool = false;
        Set<Role>  role = user_cur.getRoles();
        if(role.iterator().next() == ADMIN){
            bool = true;
        }

        model.addAttribute("bool", bool);
        return "home";
        }
        else{
            return"redirect:/login";
        }
    }
    @GetMapping("/home")
    public String home_2( Model model,
                        @AuthenticationPrincipal User uservsv) {
        model.addAttribute("title", "Главная страница");
        //System.out.println(1);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Useru user_cur = userRepos.findByUsername(user.getUsername());
        //System.out.println(user_cur.getUsername());
        Boolean bool = false;
        Set<Role>  role = user_cur.getRoles();
        if(role.iterator().next() == ADMIN){
            bool = true;
        }
        model.addAttribute("bool", bool);
        return "home";
    }


}