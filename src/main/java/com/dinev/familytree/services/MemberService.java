package com.dinev.familytree.services;

import com.dinev.familytree.dtos.MemberRelates;
import com.dinev.familytree.dtos.MemberDTO;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class MemberService {
    private final MemberRepository repository;

    @Transactional
    public Member addMember(final MemberDTO entity) {
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
            return member;
        } else {
            throw new EntityAlreadyExistsException(Member.class, entity.getFirstName());
        }
    }

    @Transactional
    public Member addMember(final Member member){
        if (member == null){
            throw new RuntimeException("Member is required.");
        }

        return repository.save(member);
    }

    @Transactional
    public MemberDTO addParents(MemberRelates members) {

        Member father = repository.findById(members.getFatherId()).get();
        Member mother = repository.findById(members.getMotherId()).get();
        Member initialMember = repository.findById(members.getMemberId()).get();

        List<Member> all = new ArrayList<>();
        all.add(father);
        all.add(mother);
        all.add(initialMember);

        Member member =  initialMember.addParents(father, mother);
        repository.saveAll(all);

        return MemberDTO.of(member);
    }

    public Member findById(final String memberId){
        return repository.findById(memberId).orElseThrow(() -> new EntityNotFoundException(Member.class, memberId));
    }
}
