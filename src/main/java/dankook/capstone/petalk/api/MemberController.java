package dankook.capstone.petalk.api;

import dankook.capstone.petalk.data.ResponseData;
import dankook.capstone.petalk.data.ResponseMessage;
import dankook.capstone.petalk.data.StatusCode;
import dankook.capstone.petalk.domain.Member;
import dankook.capstone.petalk.dto.request.UpdateMemberRequest;
import dankook.capstone.petalk.dto.response.MemberDto;
import dankook.capstone.petalk.dto.response.UpdateMemberResponse;
import dankook.capstone.petalk.service.MemberService;
import dankook.capstone.petalk.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.NoSuchElementException;

@RequestMapping("/api/member")
@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final JwtUtil jwtUtil;


    /**
     * 회원 조회
     */
    @ApiOperation(value = "", notes = "id값으로 회원 정보 조회")
    @GetMapping("/")
    public ResponseData<MemberDto> getMember(HttpServletRequest httpServletRequest){
        ResponseData<MemberDto> responseData = null;

        MemberDto memberDto;

        try{
            String token = jwtUtil.getTokenByHeader(httpServletRequest);
            jwtUtil.isValidToken(token);
            Long memberId = jwtUtil.getMemberIdByToken(token);

            Member member = memberService.findOne(memberId);

            memberDto = new MemberDto(member.getUserId(),member.getName(),member.getEmail(),member.getNickname(),member.getPetList());

            responseData = new ResponseData<>(StatusCode.OK,ResponseMessage.SUCCESS,memberDto);

            log.info(responseData.toString());
        }catch(NoSuchElementException e){
            responseData = new ResponseData<>(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER, null);
        }catch(Exception e){
            log.error(e.getMessage());
        }

        return responseData;
    }

    /**
     * 회원 수정
     */
    @ApiOperation(value = "", notes = "회원 정보 수정")
    @PutMapping("/}")
    public ResponseData<UpdateMemberResponse> updateMember(HttpServletRequest httpServletRequest,
                                                           @RequestBody @Valid UpdateMemberRequest request){
        ResponseData<UpdateMemberResponse> responseData = null;
        UpdateMemberResponse updateMemberResponse = null;

        try{
            String token = jwtUtil.getTokenByHeader(httpServletRequest);
            jwtUtil.isValidToken(token);
            Long memberId = jwtUtil.getMemberIdByToken(token);

            memberService.update(memberId,request.getName(),request.getPassword(),request.getEmail(),request.getProfileUrl());
            Member member = memberService.findOne(memberId);

            updateMemberResponse = new UpdateMemberResponse(member.getId(),member.getName(),member.getPassword(),member.getEmail(),member.getProfileUrl(),member.getNickname());
            responseData = new ResponseData<>(StatusCode.OK,ResponseMessage.SUCCESS,updateMemberResponse);
        }catch(NoSuchElementException e){
            responseData = new ResponseData<>(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER, null);
        }catch(Exception e){
            log.error(e.getMessage());
        }

        return responseData;
    }

    /**
     * 회원 삭제
     */
    @ApiOperation(value = "", notes = "회원 정보 삭제")
    @DeleteMapping("/{id}")
    public ResponseData<DeleteMemberDto> deleteMember(HttpServletRequest httpServletRequest){
        ResponseData<DeleteMemberDto> responseData = null;

        try{
            String token = jwtUtil.getTokenByHeader(httpServletRequest);
            jwtUtil.isValidToken(token);
            Long memberId = jwtUtil.getMemberIdByToken(token);

            memberService.deleteById(memberId);
            responseData = new ResponseData<>(StatusCode.OK,ResponseMessage.SUCCESS,new DeleteMemberDto(memberId));
        }catch(NoSuchElementException e){
            responseData = new ResponseData<>(StatusCode.BAD_REQUEST,ResponseMessage.NOT_FOUND_USER, new DeleteMemberDto(memberId));
            log.error(e.getMessage());
        }
        return responseData;
    }

    @Data
    @AllArgsConstructor
    static class DeleteMemberDto{
        private Long id;
    }
}
