package com.dinev.familytree.services;

import com.dinev.familytree.dtos.MemberRelates;
import com.dinev.familytree.dtos.MemberDTO;
import com.dinev.familytree.enums.Gender;
import com.dinev.familytree.exceptions.EntityAlreadyExistsException;
import com.dinev.familytree.exceptions.EntityNotFoundException;
import com.dinev.familytree.models.Member;
import com.dinev.familytree.repositories.MemberRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class MemberService {
    private final MemberRepository repository;


    @Transactional
    public Member addMember(final Member member) {
        if (member == null) {
            throw new RuntimeException("Member is required.");
        }
        member.setCreateDate(Instant.now());
        return repository.save(member);
    }

    @Transactional
    public List<Member> addMembers(final List<Member> members){
        if (members.isEmpty()){
            throw new RuntimeException("Members are required.");
        }
        members.stream().forEach(m -> m.setCreateDate(Instant.now()));
        return repository.saveAll(members);
    }

    @Transactional
    public MemberDTO addParents(MemberRelates members) {
        Member member = findById(members.getMemberId());
        if (!member.getParents().isEmpty()) {
            throw new RuntimeException("Member with ID: " + member.getId() + " already has parents.");
        }

        Member father = findById(members.getFatherId());
        Member mother = findById(members.getMotherId());

        if (father == mother || father == member || mother == member) {
            throw new RuntimeException("Enter three different members.");
        }

        if (!father.getGender().equals(Gender.MALE) || !mother.getGender().equals(Gender.FEMALE)) {
            throw new IllegalArgumentException("Parents have to be from proper gender.");
        }

        List<Member> all = new ArrayList<>();
        all.add(father);
        all.add(mother);
        all.add(member);

        member.getParents().add(father);
        if (!father.getChildren().isEmpty()){
            father.getChildren().stream().forEach(n -> member.getSiblings().add(n));
            father.getChildren().stream().forEach(n -> n.getSiblings().add(member));
        }
        father.getChildren().add(member);

        member.getParents().add(mother);
        if (!mother.getChildren().isEmpty()){
            mother.getChildren().stream().forEach(n -> member.getSiblings().add(n));
        }
        mother.getChildren().add(member);

        repository.saveAll(all);
        log.info("Parent's member '" + member.getId() + "' mother '" + mother.getId()
                + " and father " + father.getId() + " added.");

        return MemberDTO.of(member);
    }

    //TODO: Create removeParent() method.

    public Member findById(final String memberId) {
        return repository.findById(memberId).orElseThrow(() -> new EntityNotFoundException(Member.class, memberId));
    }
}
