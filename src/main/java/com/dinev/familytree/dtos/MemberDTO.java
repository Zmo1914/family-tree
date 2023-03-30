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
import org.springframework.util.StringUtils;

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
    private String gender;
    private Set<String> parentIds;
    private Set<String> childrenIds;



    public static Member to(MemberDTO source) {
        Member entity = new Member();
        BeanUtils.copyProperties(source, entity);
        entity.setGender(Gender.valueOf(source.gender));

        if (!CollectionUtils.isEmpty(source.getParentIds())) {
            source.getParentIds().forEach(p -> entity.getParents()
                    .add(Member.builder().id(p).parents(Set.of(entity)).build()));
        }

        if (!CollectionUtils.isEmpty(source.getChildrenIds())) {
            source.getChildrenIds().forEach(c -> entity.getChildren()
                    .add(Member.builder().id(c).children(Set.of(entity)).build()));
        }

        return entity;
    }

    public static List<Member> to(final List<MemberDTO> dtoList) {
        return dtoList
                .stream()
                .map(MemberDTO::to)
                .collect(Collectors.toList());
    }

    public static MemberDTO of(final Member source) {
        MemberDTO dto = new MemberDTO();
        BeanUtils.copyProperties(source, dto);

        if (!CollectionUtils.isEmpty(source.getParents())){
            dto.setParentIds(source.getParents().stream().map(Member::getId).collect(Collectors.toSet()));
        }

        if (!CollectionUtils.isEmpty(source.getChildren())){
            dto.setChildrenIds(source.getChildren().stream().map(Member::getId).collect(Collectors.toSet()));
        }

        return dto;
    }

    public static List<MemberDTO> of(final List<Member> members) {
        return members
                .stream()
                .map(MemberDTO::of)
                .collect(Collectors.toList());
    }


}
