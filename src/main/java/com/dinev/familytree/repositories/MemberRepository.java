package com.dinev.familytree.repositories;

import com.dinev.familytree.models.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

    boolean existsMemberByFirstNameAndSureNameAndLastNameIgnoreCase(String firstName, String sureName, String lastName);

}
