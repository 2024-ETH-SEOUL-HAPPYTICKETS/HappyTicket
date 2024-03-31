package com.example.demo.domain.member.repository;

import com.example.demo.domain.member.entity.Member;
import com.example.demo.utillities.auth.PrincipalDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    Page<Member> findAll (Pageable pageable);

    Optional<Member> findMemberByUsername(String username);
    Optional<Member> findMemberByEmail(String email);

    Optional<Member> findMemberByUsernameAndEmail(String username, String email);
}
