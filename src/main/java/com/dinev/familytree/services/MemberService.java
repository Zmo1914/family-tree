package com.dinev.familytree.services;

import com.dinev.familytree.dtos.MemberRelates;
import com.dinev.familytree.dtos.MemberDTO;
import com.dinev.familytree.dtos.MemberWrapper;
import com.dinev.familytree.exceptions.EntityAlreadyExistsException;
import com.dinev.familytree.models.Member;
import com.dinev.familytree.repositories.MemberRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class MemberService {
    private final MemberRepository repository;

    public MemberDTO add(final MemberDTO entity) {
        boolean isMemberAlreadyAdded = repository.existsMemberByFirstNameAndSureNameAndLastNameIgnoreCase
                (entity.getFirstName(), entity.getSureName(), entity.getLastName());

        if (!isMemberAlreadyAdded) {
            Member member = Member.builder()
                    .firstName(entity.getFirstName())
                    .sureName(entity.getSureName())
                    .lastName(entity.getLastName())
                    .gender(entity.getGender())
                    .enabled(true)
                    .birthDate(entity.getBirthDate())
                    .createDate(Instant.now())
                    .build();

            repository.save(member);
            log.info("Member " + entity.getFirstName() + " is added.");
            return MemberDTO.of(member);
        } else {
            throw new EntityAlreadyExistsException(Member.class, entity.getFirstName());
        }
    }

    public Member addParents(MemberRelates members) {

        Member father = repository.findById(members.getFatherId()).get();
        Member mother = repository.findById(members.getMotherId()).get();
        Member member = repository.findById(members.getMemberId()).get();
        List<Member> all = new ArrayList<>();
        all.add(father);
        all.add(mother);
        all.add(member);

        Member member1 =  member.addParents(father, mother);
        repository.saveAll(all);

        return member1;
    }
}
