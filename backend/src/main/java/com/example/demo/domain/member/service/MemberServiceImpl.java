package com.example.demo.domain.member.service;

import com.example.demo.domain.member.dto.SignUpDto;
import com.example.demo.domain.member.entity.Authority;
import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService{
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Optional<Member> findMemberByUsername(String username) {

        return memberRepository.findMemberByUsername(username);
    }

    @Override
    public List<Member> findMembers(Pageable pageable) {
        Page<Member> memberPage = memberRepository.findAll(pageable);
        return memberPage.getContent();
    }

    @Override
    public void signUp(SignUpDto signUpDto) throws RuntimeException {
        Member member = Member.builder()
                .username(signUpDto.getUsername())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .email(signUpDto.getEmail())
                .authorities(new ArrayList<>())
                .build();
        Authority authority = Authority.builder()
                .authority("ROLE_USER")
                .member(member)
                .build();
        member.getAuthorities().add(authority);
        memberRepository.save(member);
    }
}
