package com.example.practice.Repos;

import com.example.practice.Models.Answer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepos extends CrudRepository<Answer, Integer> {
    Answer findByIda(Integer ida);

}
