package dankook.capstone.petalk.service;

import dankook.capstone.petalk.dto.response.AuthResponse;
import dankook.capstone.petalk.dto.response.MemberDto;
import dankook.capstone.petalk.dto.response.OAuthResponse;
import dankook.capstone.petalk.entity.Member;
import dankook.capstone.petalk.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final KakaoService kakaoService;
    private final MemberService memberService;

    public AuthResponse loginWithKakao(String code) {
        AuthResponse authResponse = new AuthResponse();

        try {
            // 1. 코드 값으로 토큰 구하기
            String token = kakaoService.getTokenByCode(code);

            // 2. 토큰값으로 카카오 정보 구하기
            OAuthResponse oAuthResponse = kakaoService.getKakaoUserInfoByToken(token);

            Member member = new Member(oAuthResponse);

            memberService.join(member);

            MemberDto memberDto = new MemberDto(member);

            authResponse.setToken(token);
            authResponse.setMemberDto(memberDto);

            return authResponse;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return authResponse;
    }
}
