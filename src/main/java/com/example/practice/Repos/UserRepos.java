package com.example.practice.Repos;


import com.example.practice.Models.Useru;
import org.springframework.data.repository.CrudRepository;

public interface UserRepos extends CrudRepository<Useru, Integer> {
    Useru findByUsername(String username);
    Useru findFirstByIdu(Integer idu);
}
