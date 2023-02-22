package com.dinev.familytree.dtos;

import com.dinev.familytree.enums.Gender;
import com.dinev.familytree.models.Member;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class MemberDTO {

    private String id;
    private String firstName;
    private String sureName;
    private String lastName;
    private LocalDate birthDate;
    private Gender gender;
    private Set<MemberDTO> parents;
    private Set<MemberDTO> children;

    public static Member to(MemberDTO dto) {
        Member member = new Member();
        BeanUtils.copyProperties(dto, member);

        if (!CollectionUtils.isEmpty(dto.getParents())) {
            dto.getParents().forEach(p -> member.getParents()
                    .add(Member.builder()
                            .id(p.id)
                            .firstName(p.firstName)
                            .sureName(p.sureName)
                            .lastName(p.lastName)
                            .birthDate(p.birthDate)
                            .gender(p.gender)
                            .parents(Set.of(member))
                            .build()));
        }
        if (!CollectionUtils.isEmpty(dto.getChildren())) {
            dto.getChildren().forEach(c -> member.getChildren()
                    .add(Member.builder()
                            .id(c.id)
                            .firstName(c.firstName)
                            .sureName(c.sureName)
                            .lastName(c.lastName)
                            .birthDate(c.birthDate)
                            .gender(c.gender)
                            .children(Set.of(member))
                            .build()));
        }
        return member;
    }

    public static List<Member> to(final List<MemberDTO> dtoList) {
        return dtoList
                .stream()
                .map(MemberDTO::to)
                .collect(Collectors.toList());
    }

    public static MemberDTO of(final Member member) {
        MemberDTO dto = new MemberDTO();
        BeanUtils.copyProperties(member, dto);

        if (!CollectionUtils.isEmpty(member.getParents())) {
          member.getParents().forEach(p -> dto.getParents()
                  .add(MemberDTO.builder()
                          .id(p.getId())
                          .firstName(p.getFirstName())
                          .sureName(p.getSureName())
                          .lastName(p.getLastName())
                          .birthDate(p.getBirthDate())
                          .gender(p.getGender())
                          .parents(Set.of(dto))
                          .build()));
        }
        return dto;
    }

    public static List<MemberDTO> of(final List<Member> members){
        return members
                .stream()
                .map(MemberDTO::of)
                .collect(Collectors.toList());
    }


}
