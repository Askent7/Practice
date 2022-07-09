package com.example.practice.Controllers;


import com.example.practice.Models.*;
import com.example.practice.Repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.example.practice.Models.Role.ADMIN;

@Controller
public class QuestionController {

    @Autowired
    private AnketRepos anketRepos;

    @Autowired
    private QuestionRepos questionRepos;

    @Autowired
    private AnswerRepos answerRepos;
    @Autowired
    private UserRepos userRepos;

    @Autowired
    private StatisticsRepos statisticsRepos;

    @GetMapping("/all-test/{id}/question/{number_q}")
    public String testview(@PathVariable(value = "id") Integer id,
                           @PathVariable(value = "number_q") Integer number_q,
                           Model model){
        if(!anketRepos.existsById(id)){
            return "redirect:/all-test";
        }

        //Optional<Anket> anket = anketRepos.findById(id);
        //ArrayList<Anket> res = new ArrayList<>();
        //anket.ifPresent(res::add);
        // model.addAttribute("anket", res);
        Anket anket = anketRepos.findFirstById(id);
        Iterable<Question> questions = anket.getQuestion();

        List<Question> questions_list = new ArrayList<Question>();
        questions.iterator().forEachRemaining(questions_list::add);

        Question question_1 = new Question();

        for(int i = 0; i < questions_list.size(); i++){
            if(questions_list.get(i).getNumberq() == number_q)
                question_1 = questions_list.get(i);
        }

        //terable<Question> questions_1_new = Arrays.asList(question_1);
        //model.addAttribute("question", questions_1_new);

        model.addAttribute("question_name", question_1.getNameq());

        List<Answer> answers = question_1.getAnswers();
        model.addAttribute("answer", answers);

        //Question question_for = new Question();
         model.addAttribute("question_for", question_1);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Useru user_cur = userRepos.findByUsername(user.getUsername());
        Boolean bool = false;
        Set<Role> role = user_cur.getRoles();
        if(role.iterator().next() == ADMIN){
            bool = true;
        }
        model.addAttribute("bool", bool);

         if(questions_list.size() == number_q)
             model.addAttribute("text_button", "Завершить тест");
         else
             model.addAttribute("text_button", "Следующий вопрос");

        if(question_1.getOneq())
            return "question-one";
        else
            return "question-many";
    }

    //Дописать функцию на сохранение ответов к пользователю
    @RequestMapping(value = "/all-test/{id}/question/{number_q}", method = RequestMethod.POST, params = "next")
    public String saveAnswer(@ModelAttribute("question_for") Question question,
                             @PathVariable(value = "id") Integer id,
                             @PathVariable(value = "number_q") Integer number_q,
                             @RequestParam(value = "check" , required = false) int[] check,
                             @AuthenticationPrincipal User user){
        String name_current_user = user.getUsername();
        Useru useru = userRepos.findByUsername(name_current_user);

        Anket anket = anketRepos.findFirstById(id);
        Iterable<Question> questions = anket.getQuestion();

        List<Question> questions_list = new ArrayList<Question>();
        questions.iterator().forEachRemaining(questions_list::add);

        Question question_cur = new Question();

        for(int i = 0; i < questions_list.size(); i++){
            if(questions_list.get(i).getNumberq() == number_q)
                question_cur = questions_list.get(i);
        }

        if(check != null){
            for(int i = 0; i < check.length; i++)
            {

                Statistics statistics = new Statistics(useru.getIdu(), check[i], question_cur.getIdq(), id);
                statisticsRepos.save(statistics);
            }

        }else{
           System.out.println("Я сломался");
        }

        int number_next = number_q + 1;

        if(questions_list.size() == number_q)
            return "redirect:/all-test/";
        else
            return "redirect:/all-test/" + id + "/question/" + number_next;
    }
}
