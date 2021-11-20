package dankook.capstone.petalk.api;

import dankook.capstone.petalk.data.ResponseData;
import dankook.capstone.petalk.data.ResponseMessage;
import dankook.capstone.petalk.data.StatusCode;
import dankook.capstone.petalk.dto.request.KakaoRequest;
import dankook.capstone.petalk.dto.request.SignInRequest;
import dankook.capstone.petalk.dto.response.AuthResponse;
import dankook.capstone.petalk.dto.response.MemberDto;
import dankook.capstone.petalk.dto.response.ValidTokenResponse;
import dankook.capstone.petalk.entity.Member;
import dankook.capstone.petalk.dto.request.SignUpRequest;
import dankook.capstone.petalk.dto.response.SignUpResponse;
import dankook.capstone.petalk.service.AuthService;
import dankook.capstone.petalk.service.MemberService;
import dankook.capstone.petalk.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.NoSuchElementException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final MemberService memberService;
    private final AuthService authService;

    @ApiOperation(value = "", notes = "신규 회원 생성")
    @PostMapping("/signup")
    public ResponseData<SignUpResponse> singUp(@RequestBody SignUpRequest request){
        ResponseData<SignUpResponse> responseData = null;

        try{
            Member member = request.toMemberEntity();
            Long savedMemberId = memberService.join(member);

            String token = jwtUtil.generateToken(savedMemberId, member.getPlatformId());
            SignUpResponse jwtResponse = new SignUpResponse(token);
            responseData = new ResponseData<>(StatusCode.OK, ResponseMessage.SUCCESS, jwtResponse);

            log.info(responseData.toString());

        } catch(IllegalArgumentException e){
            return new ResponseData<>(StatusCode.BAD_REQUEST, ResponseMessage.MEMBER_CREATION_FAIL, null);
        } catch(Exception e){
            log.error(e.getMessage());
        }

        return responseData;
    }

    @ApiOperation(value = "", notes = "아이디 비밀번호로 로그인")
    @PostMapping("/signin")
    public ResponseData<MemberDto> getMemberById(@RequestBody @Valid SignInRequest request) {
        ResponseData<MemberDto> responseData = null;

        MemberDto memberDto;

        try{
            Member findMember = memberService.findOneByUserId(request.getUserId());

            String token = jwtUtil.generateToken(findMember.getId(), findMember.getPlatformId());

            if(memberService.validateMember(findMember, request.getPassword())){
                memberDto = new MemberDto(findMember, token);
                responseData = new ResponseData<>(StatusCode.OK, ResponseMessage.SUCCESS, memberDto);
            }else {throw new NoSuchElementException();}
        }catch(NoSuchElementException e){
            responseData = new ResponseData<>(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER, null);
        }catch(Exception e){
            log.error(e.getMessage());
        }

        return responseData;
    }

    @PostMapping("/kakao")
    public ResponseEntity<AuthResponse> loginWithKakao(@RequestBody KakaoRequest kakaoRequest) {
        return ResponseEntity.ok(authService.loginWithKakao(kakaoRequest));
    }

    @PostMapping("/token")
    public ResponseData<ValidTokenResponse> checkToken(HttpServletRequest httpServletRequest){
        try {
            String token = jwtUtil.getTokenByHeader(httpServletRequest);
            jwtUtil.isValidToken(token);

            return new ResponseData<>(StatusCode.OK, ResponseMessage.SUCCESS, new ValidTokenResponse(token));
        }catch(JwtException e) {
            return ResponseData<>(StatusCode.UNAUTHORIZED, e.getMessage(), new ValidTokenResponse(token));
        }
    }

}
