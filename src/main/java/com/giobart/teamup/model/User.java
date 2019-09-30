package com.giobart.teamup.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String name;

    private String surname;

    private String degreeCourse;

    private String email;

    private String skills;

    private Boolean mentor;

    @ManyToOne
    @JoinColumn
    private Group group;

    @ManyToMany(mappedBy = "groupFollowers")
    private List<Group> followingGroups;

    @Transient
    private String passwordConfirm;

    @ManyToMany
    private Set<Role> roles = new HashSet<>();

}
