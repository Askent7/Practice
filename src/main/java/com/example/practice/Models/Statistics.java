package com.example.practice.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Statistics {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer ids;

    private Integer idu;
    private Integer ida;
    private Integer idq;
    private Integer idanket;

    public Statistics() {
    }

    public Statistics(Integer idu, Integer ida, Integer idq, Integer idanket) {
        this.idu = idu;
        this.ida = ida;
        this.idq = idq;
        this.idanket = idanket;
    }

    public Integer getIds() {
        return ids;
    }

    public void setIds(Integer ids) {
        this.ids = ids;
    }

    public Integer getIdu() {
        return idu;
    }

    public void setIdu(Integer idu) {
        this.idu = idu;
    }

    public Integer getIda() {
        return ida;
    }

    public void setIda(Integer ida) {
        this.ida = ida;
    }

    public Integer getIdq() {
        return idq;
    }

    public void setIdq(Integer idq) {
        this.idq = idq;
    }

    public Integer getIdanket() {
        return idanket;
    }

    public void setIdanket(Integer idanket) {
        this.idanket = idanket;
    }
}
