package com.example.practice.Repos;

import com.example.practice.Models.Anket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AnketRepos extends CrudRepository<Anket, Integer> {

    Anket findByNamequestionnaire(String namequestionnaire);

    Anket findFirstById(Integer id);




}
