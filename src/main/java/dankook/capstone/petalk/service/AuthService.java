package dankook.capstone.petalk.service;

import dankook.capstone.petalk.data.ResponseMessage;
import dankook.capstone.petalk.data.StatusCode;
import dankook.capstone.petalk.dto.request.SignUpRequest;
import dankook.capstone.petalk.dto.response.AuthResponse;
import dankook.capstone.petalk.dto.response.MemberDto;
import dankook.capstone.petalk.dto.response.OAuthResponse;
import dankook.capstone.petalk.dto.response.SignUpResponse;
import dankook.capstone.petalk.entity.Member;
import dankook.capstone.petalk.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final KakaoService kakaoService;
    private final MemberService memberService;
    private final JwtUtil jwtUtil;
    private final S3Uploader s3Uploader;

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
