package com.example.demo.utillities.auth;

import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class PrincipalDetailsService implements UserDetailsService {
    @Autowired
    private MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("init loadUserByUsername username={}",username);
        Optional<Member> member = memberRepository.findMemberByUsername(username);

        if(member.isEmpty())
            throw new UsernameNotFoundException(username);
        log.info("loadUserByUsername user={}",member.get());

        return new PrincipalDetails(member.get());
    }
}
