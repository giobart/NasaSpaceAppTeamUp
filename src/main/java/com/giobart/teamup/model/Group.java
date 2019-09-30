package com.giobart.teamup.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "competitiongroup")
@Data
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String telegramGroup;

    @OneToMany()
    private List<User> groupmates = new ArrayList<>();

    @ManyToMany
    private List<User> groupFollowers = new ArrayList<>();

}
