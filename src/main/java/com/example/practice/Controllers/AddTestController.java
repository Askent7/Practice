package com.example.practice.Controllers;

import com.example.practice.Models.*;
import com.example.practice.Repos.AnketRepos;
import com.example.practice.Repos.AnswerRepos;
import com.example.practice.Repos.QuestionRepos;
import com.example.practice.Repos.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.sql.init.SqlR2dbcScriptDatabaseInitializer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

import static com.example.practice.Models.Role.ADMIN;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class AddTestController {
    @Autowired
    private AnketRepos anketRepos;

    @Autowired
    private AnswerRepos answerRepos;

    @Autowired
    private QuestionRepos questionRepos;

    @Autowired
    private UserRepos userRepos;

    @GetMapping("/addtest")
    public String addtest( Model model) {
        model.addAttribute("title", "Страница про нас");

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Useru user_cur = userRepos.findByUsername(user.getUsername());
        Boolean bool = false;
        Set<Role>  role = user_cur.getRoles();
        if(role.iterator().next() == ADMIN){
            bool = true;
        }
        model.addAttribute("bool", bool);

        return "addtest";
    }


    @PostMapping("/addtest")
    public String addTest2(@RequestParam String namequestionnaire, @RequestParam String author, Model model){
        Anket anket = new Anket(namequestionnaire, author);
        Question question1 = new Question("", 1, true);
        anket.setQuestion(Arrays.asList(question1));
        anketRepos.save(anket);
        Anket list = anketRepos.findByNamequestionnaire(namequestionnaire);
        int id = list.getId();
        Question question = questionRepos.findFirstByOrderByIdqDesc();
        int id2 = question.getIdq();
        System.out.println("Редирект на 1 вопрос");
        return "redirect:/addquestion/anket/" + id +"/question/" + id2 +"/1";
    }

    @GetMapping("/addquestion/anket/{id}/question/{idq}/{number}")
    public String addquestion(@PathVariable(value = "id") Integer id, @PathVariable(value = "idq") Integer idq,Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Useru user_cur = userRepos.findByUsername(user.getUsername());
        Boolean bool = false;
        Set<Role>  role = user_cur.getRoles();
        if(role.iterator().next() == ADMIN){
            bool = true;
        }

        model.addAttribute("bool", bool);

        Question question = questionRepos.findByIdq(idq);
        model.addAttribute("text_q", question.getNameq());
        Boolean flag = false;
        if (question.getAnswers().size() < 2)
            flag = true;
        model.addAttribute("flag",flag);


        return "add-question";
    }

    @RequestMapping(value = "/addquestion/anket/{id}/question/{idq}/{number}", method = RequestMethod.POST, params = "add")
    public String addsnswertest(@PathVariable(value = "id") Integer id,
                                @PathVariable(value = "idq") Integer idq,
                                @PathVariable(value = "number") Integer number,
                                @RequestParam String answer,
                                @RequestParam String name_q,
                                Model model ){

        Question question = questionRepos.findByIdq(idq);
        Answer answer1 = new Answer(answer, false);
        //question.setAnswers(Arrays.asList(answer1));
        question.getAnswers().add(answer1);
        question.setNameq(name_q);
        questionRepos.save(question);

        return "redirect:/addquestion/anket/" + id +"/question/" + idq + "/" + number;
    }

    @RequestMapping(value = "/addquestion/anket/{id}/question/{idq}/{number}", method = RequestMethod.POST, params = "next")
    public String addnewquestion(@PathVariable(value = "id") Integer id,
                                 @PathVariable(value = "idq") Integer idq,
                                 @PathVariable(value = "number") Integer number,
                                 @RequestParam String answer,
                                 @RequestParam String name_q,
                                 @RequestParam(value = "checkbox", required = false) String checkbox,
                                 Model model ){


        System.out.println(checkbox);
        Question question = questionRepos.findByIdq(idq);

        boolean flag = false;
        if(question.getAnswers().size() >=2){
        question.setNameq(name_q);
        boolean boll = true;
        if(checkbox == null){
            boll = false;
        }
        question.setOneq(boll);
        questionRepos.save(question);
        Question question1 = new Question("", number + 1, true);
        Anket anket = anketRepos.findFirstById(id);
        anket.getQuestion().add(question1);
        anketRepos.save(anket);

        Question question_r = questionRepos.findFirstByOrderByIdqDesc();

        int number_next= number +1;

        model.addAttribute("flag",flag);
        return "redirect:/addquestion/anket/" + id +"/question/" + question_r.getIdq() + "/" + number_next;
        }
        else {
            flag = true;
            model.addAttribute("flag",flag);
            return "redirect:/addquestion/anket/" + id +"/question/" + idq + "/" + number;
        }
    }

    @RequestMapping(value = "/addquestion/anket/{id}/question/{idq}/{number}", method = RequestMethod.POST, params = "end")
    public String endtest(@PathVariable(value = "id") Integer id,
                                @PathVariable(value = "idq") Integer idq,
                                @PathVariable(value = "number") Integer number,
                                @RequestParam String answer,
                                Model model ){


        questionRepos.deleteById(idq);
        return "redirect:/all-test/";
    }



}
