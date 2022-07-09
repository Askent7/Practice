package com.example.practice.Models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer ida;

    private String answer;
    private Boolean breply;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "idanswert")
    private List<Statistics> statistics;



    public Answer() {
    }

    public Answer(String answer, Boolean breply) {
        this.answer = answer;
        this.breply = breply;
    }

    public Integer getIda() {
        return ida;
    }

    public void setIda(Integer ida) {
        this.ida = ida;
    }

    public Boolean getBreply() {
        return breply;
    }

    public void setBreply(Boolean breply) {
        this.breply = breply;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<Statistics> getStatistics() {
        return statistics;
    }

    public void setStatistics(List<Statistics> statistics) {
        this.statistics = statistics;
    }
}
