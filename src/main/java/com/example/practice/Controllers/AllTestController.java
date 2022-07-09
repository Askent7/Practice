package com.example.practice.Controllers;

import com.example.practice.Models.Anket;
import com.example.practice.Models.Question;
import com.example.practice.Models.Role;
import com.example.practice.Models.Useru;
import com.example.practice.Repos.AnketRepos;
import com.example.practice.Repos.QuestionRepos;
import com.example.practice.Repos.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import static com.example.practice.Models.Role.ADMIN;

@Controller
public class AllTestController {

    @Autowired
    private AnketRepos anketRepos;

    @Autowired
    private QuestionRepos questionRepos;

    @Autowired
    private UserRepos userRepos;


    @GetMapping("/all-test")
        public String BlogMain(Model model){
        Iterable<Anket> ankets = anketRepos.findAll();

        model.addAttribute("ankets", ankets);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Useru user_cur = userRepos.findByUsername(user.getUsername());
        Boolean bool = false;
        Set<Role> role = user_cur.getRoles();
        if(role.iterator().next() == ADMIN){
            bool = true;
        }
        model.addAttribute("bool", bool);

        return "all-test";
    }


    @GetMapping("/all-test/{id}/eddit")
    public String testeddit(@PathVariable(value = "id") Integer id ,Model model){
        if(!anketRepos.existsById(id)){
            return "redirect:/all-test";
        }

        Optional<Anket> anket = anketRepos.findById(id);
        ArrayList<Anket> res = new ArrayList<>();
        anket.ifPresent(res::add);
        model.addAttribute("anket", res);
        return "anket-eddit";
    }

    @PostMapping("/all-test/{id}/eddit")
    public String UpdateTest(@PathVariable(value = "id") Integer id,@RequestParam String name_questionnaire, @RequestParam String author, Model model){
        Anket anket = anketRepos.findById(id).orElseThrow();

        anket.setNamequestionnaire(name_questionnaire);
        anket.setAuthor(author);

        anketRepos.save(anket);

        return "redirect:/all-test";
    }




}
