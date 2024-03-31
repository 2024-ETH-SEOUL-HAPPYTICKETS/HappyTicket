package com.example.demo.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KakaoMemberInfo {
    private long id;
    private String connected_at;
    private KakaoProperties properties;
    private KakaoAccount kakao_account;
}
