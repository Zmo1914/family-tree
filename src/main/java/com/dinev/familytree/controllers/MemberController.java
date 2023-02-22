package com.dinev.familytree.controllers;


import com.dinev.familytree.dtos.MemberRelates;
import com.dinev.familytree.dtos.MemberDTO;
import com.dinev.familytree.services.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("familytree/v1/member")
public class MemberController {

    private final MemberService service;

    @PostMapping("/")
    public ResponseEntity<?> addMember(@RequestBody final MemberDTO dto){
       MemberDTO newMember = service.add(dto);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(newMember);
    }

@PostMapping("/addParents")
    public ResponseEntity<?> addParents(@RequestBody MemberRelates members){

        service.addParents(members);



        return null;
    }
}
