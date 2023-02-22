package com.dinev.familytree.dtos;

import com.dinev.familytree.models.Member;
import com.dinev.familytree.repositories.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberWrapper {

    List<String> memberIds;
}
