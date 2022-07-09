package com.example.practice.Models;

import javax.persistence.*;
import java.util.List;

@Entity

public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idq;

    private String nameq;
    private Integer numberq;
    private Boolean oneq;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "questionid")
    private List<Answer> answers;



    public Question() {
    }

    public Question(String nameq, Integer numberq, Boolean oneq) {
        this.nameq = nameq;
        this.numberq = numberq;
        this.oneq = oneq;
    }

    public Integer getIdq() {
        return idq;
    }

    public void setIdq(Integer idq) {
        this.idq = idq;
    }

    public String getNameq() {
        return nameq;
    }

    public void setNameq(String nameq) {
        this.nameq = nameq;
    }

    public Integer getNumberq() {
        return numberq;
    }

    public void setNumberq(Integer numberq) {
        this.numberq = numberq;
    }

    public Boolean getOneq() {
        return oneq;
    }

    public void setOneq(Boolean oneq) {
        this.oneq = oneq;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
