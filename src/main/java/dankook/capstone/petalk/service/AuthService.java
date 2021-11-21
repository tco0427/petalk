package dankook.capstone.petalk.service;

import dankook.capstone.petalk.dto.request.KakaoRequest;
import dankook.capstone.petalk.dto.response.AuthResponse;
import dankook.capstone.petalk.dto.response.MemberDto;
import dankook.capstone.petalk.entity.Member;
import dankook.capstone.petalk.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    public AuthResponse loginWithKakao(KakaoRequest request) {

        AuthResponse authResponse = null;
        boolean isUser = false;

        try{
            Member memberByPlatformId = memberService.findByPlatformId(request.getPlatformId());
            if(memberByPlatformId != null) {
                isUser = true;
            }

            if(!isUser) {
                Member member = request.toMemberEntity();

                Long savedMemberId = memberService.join(member);

                String token = jwtUtil.generateToken(savedMemberId, member.getPlatformId());

                MemberDto memberDto = new MemberDto(member);

                authResponse = new AuthResponse(token, memberDto);
            }else{
                String token = jwtUtil.generateToken(memberByPlatformId.getId(), memberByPlatformId.getPlatformId());

                MemberDto memberDto = new MemberDto(memberByPlatformId);

                authResponse = new AuthResponse(token, memberDto);
            }

        } catch(IllegalArgumentException e){
            log.error(e.getMessage(), e);
        } catch(Exception e){
            log.error(e.getMessage(), e);
        }

        return authResponse;
    }
}
