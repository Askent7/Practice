package com.example.practice.Repos;
import com.example.practice.Models.Anket;
import com.example.practice.Models.Answer;
import com.example.practice.Models.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepos extends CrudRepository<Question, Integer>{
    //Anket findByNamequestionnaire(String namequestionnaire);
    //SELECT * FROM anket a INNER JOIN question q ON a.id = q.idanket
    Question findByNameq(String nameq);

    Question findByIdq(Integer idq);

    Question findFirstByOrderByIdqDesc();





}
