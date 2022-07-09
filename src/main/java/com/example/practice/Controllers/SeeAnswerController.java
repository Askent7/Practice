package com.example.practice.Controllers;

import com.example.practice.Models.*;
import com.example.practice.Repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.example.practice.Models.Role.ADMIN;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class SeeAnswerController {

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

    @GetMapping("/seeanswer")
    public String see_all_user(Model model){
        Iterable<Useru> userus = userRepos.findAll();

        model.addAttribute("user", userus);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Useru user_cur = userRepos.findByUsername(user.getUsername());
        Boolean bool = false;
        Set<Role> role = user_cur.getRoles();
        if(role.iterator().next() == ADMIN){
            bool = true;
        }
        model.addAttribute("bool", bool);


        return "see-all-users";
    }

    @GetMapping("/seeanswer/{idu}")
    public String see_user(@PathVariable(value = "idu") Integer idu,
                           Model model){

      Useru user_cur = userRepos.findFirstByIdu(idu);
      List<Statistics> statisticsList = statisticsRepos.findAllByIdu(idu);
      List<Anket> anketList = new ArrayList<>();

      //получили лист анкет, которые встречаются
      int mas[] = new int[statisticsList.size()];
      for(int i = 0; i < mas.length; i++){
          boolean bool = true;
          for(int y = 0; y < mas.length; y++){
              if(mas[y] == statisticsList.get(i).getIdanket()){
                  bool = false;
              }
          }
          if(bool){
              mas[i] = statisticsList.get(i).getIdanket();
              anketList.add(anketRepos.findFirstById(mas[i]));
          }
         }

        List<Anket> anketList_cur = new ArrayList<>();
        //переформировка анкеты
        for(int anket_number = 0; anket_number < anketList.size(); anket_number++){
            Anket anket = new Anket();

            anket.setNamequestionnaire(anketList.get(anket_number).getNamequestionnaire());

            List<Question> questions= new ArrayList<>();

            //установили названия вопросов
            for(int question_number = 0; question_number < anketList.get(anket_number).getQuestion().size(); question_number++){
                Question question_cur = new Question(anketList.get(anket_number).getQuestion().get(question_number).getNameq(), question_number, anketList.get(anket_number).getQuestion().get(question_number).getOneq());
                List<Answer> answers_list = new ArrayList<>();
                for(int static_number = 0; static_number < statisticsList.size(); static_number++){
                    Answer answer = answerRepos.findByIda(statisticsList.get(static_number).getIda());
                    for(int i = 0; i < anketList.get(anket_number).getQuestion().get(question_number).getAnswers().size(); i++){
                        if(answer.getIda() == anketList.get(anket_number).getQuestion().get(question_number).getAnswers().get(i).getIda()){
                            answers_list.add(answer);
                        }
                    }

                }
                question_cur.setAnswers(answers_list);
                questions.add(question_cur);
            }
            //установили ответы

            anket.setQuestion(questions);
            anketList_cur.add(anket);
        }

        model.addAttribute("anket", anketList_cur);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Useru user_cur2 = userRepos.findByUsername(user.getUsername());
        Boolean bool = false;
        Set<Role>  role = user_cur2.getRoles();
        if(role.iterator().next() == ADMIN){
            bool = true;
        }
        model.addAttribute("bool", bool);
        return "see-all-answer";
    }


}
