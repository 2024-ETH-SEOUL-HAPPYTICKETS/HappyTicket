package com.example.demo.domain.member.controller;

import com.example.demo.domain.member.dto.SignUpDto;
import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.service.MemberService;
import com.example.demo.utillities.auth.PrincipalDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/member")
@Slf4j
public class MemberController {
    @Autowired
    private MemberService memberService;
    @GetMapping("/detail/{username}")
    ResponseEntity<Member> getMemberDetail(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable String username) {

        if (principalDetails.getUsername().equals(username) || principalDetails.getAuthorities().toString().contains("ROLE_ADMIN")){
            Optional<Member> optionalMember = memberService.findMemberByUsername(username);
            return ResponseEntity.ok(optionalMember.get());
        } else {
            return ResponseEntity.status(403).build();
        }
    }
    @GetMapping("/all")
    ResponseEntity<List<Member>> getMemberDetail(@PageableDefault(value= 10, page = 0) Pageable pageable) {
        List<Member> memberList = memberService.findMembers(pageable);
        return ResponseEntity.ok(memberList);
    }

    @PostMapping("/signup")
    ResponseEntity<?> signUp (@RequestBody SignUpDto signUpDto) {
        try {
            memberService.signUp(signUpDto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
