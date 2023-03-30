package com.dinev.familytree.models;

import com.dinev.familytree.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serial;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "members")
public class Member extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 6772742332938445211L;

    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;

    @Column(name = "sure_name", nullable = false, length = 20)
    private String sureName;

    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @ManyToMany
    private Set<Member> parents = new HashSet<>();

    @ManyToMany(mappedBy = "parents")
    private Set<Member> children = new HashSet<>();

    public Member removeParent(Member parent){
        if (parents.isEmpty()){
            throw new RuntimeException();
        }
        parents.remove(parent);
        parent.getChildren().remove(parent);


        return this;
    }
}
