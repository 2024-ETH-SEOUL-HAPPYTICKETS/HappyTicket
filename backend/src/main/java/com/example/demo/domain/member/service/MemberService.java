package com.example.demo.domain.member.service;

import com.example.demo.domain.member.dto.SignUpDto;
import com.example.demo.domain.member.entity.Member;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

public interface MemberService {
    Optional<Member> findMemberByUsername(String username);

    List<Member> findMembers(Pageable pageable);

    void signUp(SignUpDto signUpDto);
}
