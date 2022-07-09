package com.example.practice.Models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="usr")
public class Useru {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idu;
    private String username;
    private String password;
    private boolean active;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "userrole", joinColumns = @JoinColumn(name="iduser"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public Integer getIdu() {
        return idu;
    }

    public void setIdu(Integer idu) {
        this.idu = idu;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
