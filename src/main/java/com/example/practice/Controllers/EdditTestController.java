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
public class EdditTestController {

    @Autowired
    private AnketRepos anketRepos;

    @Autowired
    private AnswerRepos answerRepos;

    @Autowired
    private QuestionRepos questionRepos;

    @Autowired
    private UserRepos userRepos;

    @GetMapping("/eddittest")
    public String eddit_test(Model model){
        Iterable<Anket> ankets = anketRepos.findAll();
        model.addAttribute("ankets", ankets);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Useru user_cur = userRepos.findByUsername(user.getUsername());
        Boolean bool = false;
        Set<Role>  role = user_cur.getRoles();
        if(role.iterator().next() == ADMIN){
            bool = true;
        }
        model.addAttribute("bool", bool);

        return "eddit-test";
    }

    @GetMapping("/eddittest/{id}")
    public String eddit_test_id(@PathVariable(value = "id") Integer id,
                                Model model){
        Optional<Anket> anket = anketRepos.findById(id);
        ArrayList<Anket> res = new ArrayList<>();
        anket.ifPresent(res::add);
        model.addAttribute("anket", res);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Useru user_cur = userRepos.findByUsername(user.getUsername());
        Boolean bool = false;
        Set<Role>  role = user_cur.getRoles();
        if(role.iterator().next() == ADMIN){
            bool = true;
        }
        model.addAttribute("bool", bool);

        return "anket-eddit";
    }

    @RequestMapping(value = "/eddittest/{id}", method = RequestMethod.POST, params = "next")
    public String update_test(@PathVariable(value = "id") Integer id,
                              @RequestParam String name_questionnaire,
                              @RequestParam String author, Model model){
        Anket anket = anketRepos.findById(id).orElseThrow();
        anket.setNamequestionnaire(name_questionnaire);
        anket.setAuthor(author);
        anketRepos.save(anket);

        return "redirect:/eddittest/{id}/question/1";
    }

    @GetMapping("eddittest/{id}/question/{number_q}")
    public String update_question(@PathVariable(value = "id") Integer id,
                                  @PathVariable(value = "number_q") Integer number_q,
                                  Model model){

        Anket anket = anketRepos.findFirstById(id);
        Iterable<Question> questions = anket.getQuestion();

        List<Question> questions_list = new ArrayList<Question>();
        questions.iterator().forEachRemaining(questions_list::add);

        Question question_1 = new Question();
        for(int i = 0; i < questions_list.size(); i++){
            if(questions_list.get(i).getNumberq() == number_q)
                question_1 = questions_list.get(i);
        }
        model.addAttribute("question_name", question_1.getNameq());
        List<Answer> answers = question_1.getAnswers();
        model.addAttribute("answer", answers);
        model.addAttribute("question_for", question_1);


        if(questions_list.size() == number_q)
            model.addAttribute("text_button", "Завершить редактирование");
        else
            model.addAttribute("text_button", "Следующий вопрос");

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Useru user_cur = userRepos.findByUsername(user.getUsername());
        Boolean bool = false;
        Set<Role>  role = user_cur.getRoles();
        if(role.iterator().next() == ADMIN){
            bool = true;
        }
        model.addAttribute("bool", bool);


        return "update-question";
    }


    //Дописать функцию на сохранение ответов к пользователю
    @RequestMapping(value = "eddittest/{id}/question/{number_q}", method = RequestMethod.POST, params = "next")
    public String save_update_question(@ModelAttribute("question_for") Question question,
                                       @PathVariable(value = "id") Integer id,
                                       @PathVariable(value = "number_q") Integer number_q,
                                       @RequestParam(value = "update" , required = false) String[] check){


        int number_next = number_q + 1;

        Anket anket = anketRepos.findFirstById(id);
        Iterable<Question> questions = anket.getQuestion();

        List<Question> questions_list = new ArrayList<Question>();
        questions.iterator().forEachRemaining(questions_list::add);

        Question question_1 = new Question();
        for(int i = 0; i < questions_list.size(); i++){
            if(questions_list.get(i).getNumberq() == number_q)
                question_1 = questions_list.get(i);
        }

        List<Answer> answers = question_1.getAnswers();

        for(int i = 0; i < check.length; i++){
            answers.get(i).setAnswer(check[i]);
        }

        questionRepos.save(question_1);

        if(questions_list.size() == number_q)
            return "redirect:/eddittest";
        else
            return "redirect:/eddittest/" + id + "/question/" + number_next;
    }


    @RequestMapping(value = "/eddittest/{id}", method = RequestMethod.POST, params = "delete")
    public String delete_test(@PathVariable(value = "id") Integer id, Model model){
        Anket anket = anketRepos.findById(id).orElseThrow();
        anketRepos.delete(anket);
        return "redirect:/eddittest";
    }


}
