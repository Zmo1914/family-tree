package com.dinev.familytree.controllers;

import com.dinev.familytree.dtos.MemberRelates;
import com.dinev.familytree.models.Member;
import com.dinev.familytree.services.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("family-tree/v1/member")
public class MemberController {

    private final MemberService service;

    @GetMapping("/{memberId}")
    public ResponseEntity<?> getMember(@PathVariable final String memberId){
        return null;
    }

    @PostMapping("/add-member")
    public ResponseEntity<?> addMember(@RequestBody final Member member) {
        Member newMember = service.addMember(member);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(newMember);
    }

    @PostMapping("/add-members")
    public ResponseEntity<?> addMembers(@RequestBody final List<Member> members){
        List<Member> entities = service.addMembers(members);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(entities);
    }

    @PostMapping("/addParents")
    public ResponseEntity<?> addParents(@RequestBody final MemberRelates members) {
        return ResponseEntity.ok().body(service.addParents(members));
    }
}
