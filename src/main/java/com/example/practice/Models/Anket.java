package com.example.practice.Models;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Anket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String namequestionnaire;
    private String author;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "idanket")
    private List<Question> question;


    public Anket() {

    }

    public Anket(String namequestionnaire, String author) {
        this.namequestionnaire = namequestionnaire;
        this.author = author;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNamequestionnaire() {
        return namequestionnaire;
    }

    public void setNamequestionnaire(String namequestionnaire) {
        this.namequestionnaire = namequestionnaire;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<Question> getQuestion() {
        return question;
    }

    public void setQuestion(List<Question> question) {
        this.question = question;
    }
}
