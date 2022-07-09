package com.example.practice.Repos;


import com.example.practice.Models.Statistics;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface StatisticsRepos extends CrudRepository<Statistics, Integer> {

    List<Statistics> findAllByIdu(Integer idu);
}
