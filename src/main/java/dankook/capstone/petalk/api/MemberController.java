package dankook.capstone.petalk.api;

import dankook.capstone.petalk.data.ResponseData;
import dankook.capstone.petalk.data.ResponseMessage;
import dankook.capstone.petalk.data.StatusCode;
import dankook.capstone.petalk.domain.Member;
import dankook.capstone.petalk.domain.Pet;
import dankook.capstone.petalk.service.MemberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RequestMapping("/api/member")
@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원 생성
     */
    @ApiOperation(value = "", notes = "신규 회원 생성")
    @PostMapping("/new")
    public ResponseData<CreateMemberResponse> saveMember(@RequestBody @Valid CreateMemberRequest request) {
        ResponseData<CreateMemberResponse> responseData;
        CreateMemberResponse createMemberResponse = null;

        try {

            Member member = new Member();
            member.setUserId(request.getUserId());
            member.setName(request.getName());
            member.setPassword(request.getPassword());
            member.setProfileUrl(request.getProfileUrl());
            member.setEmail(request.getEmail());

            Long id = memberService.join(member);

            createMemberResponse = new CreateMemberResponse(id, member.getUserId(), member.getName(), member.getEmail());
            responseData = new ResponseData<>(StatusCode.OK, ResponseMessage.SUCCESS, createMemberResponse);

        }catch(IllegalStateException e){
            responseData = new ResponseData<>(StatusCode.BAD_REQUEST, ResponseMessage.MEMBER_CREATION_FAIL, createMemberResponse);
            log.error(e.getMessage());
        }catch(Exception e){
            responseData = new ResponseData<>(StatusCode.BAD_REQUEST, ResponseMessage.MEMBER_CREATION_FAIL,createMemberResponse);
            log.error(e.getMessage());
        }
        return responseData;
    }


    @Data
    @AllArgsConstructor
    static class CreateMemberResponse{
        private Long id;
        private String userId;
        private String name;
        private String email;
    }

    @Data
    static class CreateMemberRequest{
        private String userId;
        private String password;
        private String name;
        private String email;
        private String profileUrl;
    }


    /**
     * 회원 조회
     */

    @ApiOperation(value = "", notes = "id값으로 회원 정보 조회")
    @GetMapping("/{id}")
    public ResponseData<MemberDto> getMemberById(@ApiParam("회원 id") @PathVariable("id") Long id){
        log.info("getMemberById : " + id);

        ResponseData<MemberDto> responseData = null;

        MemberDto memberDto = null;

        try{
            Member member = memberService.findOne(id).get();
            memberDto = new MemberDto(member.getUserId(),member.getName(),member.getEmail());
            responseData = new ResponseData<>(StatusCode.OK,ResponseMessage.SUCCESS,memberDto);
            log.info(responseData.toString());
        }catch(NoSuchElementException e){
            responseData = new ResponseData<>(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER, memberDto);
        }catch(Exception e){
            log.error(e.getMessage());
        }

        return responseData;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto{
        private String userId;
        private String name;
        private String email;
    }

    /**
     * 회원 수정
     */
    @ApiOperation(value = "", notes = "회원 정보 수정")
    @PutMapping("/{id}")
    public ResponseData<UpdateMemberResponse> updateMember(@PathVariable("id") Long id,
                                                           @RequestBody @Valid UpdateMemberRequest request){
        ResponseData<UpdateMemberResponse> responseData = null;
        UpdateMemberResponse updateMemberResponse = null;

        try{
            memberService.update(id,request.getName(),request.getPassword(),request.getEmail(),request.getProfileUrl());
            Member member = memberService.findOne(id).get();

            updateMemberResponse = new UpdateMemberResponse(member.getId(),member.getName(),member.getPassword(),member.getEmail(),member.getProfileUrl());
            responseData = new ResponseData<>(StatusCode.OK,ResponseMessage.SUCCESS,updateMemberResponse);
        }catch(NoSuchElementException e){
            responseData = new ResponseData<>(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER, updateMemberResponse);
        }catch(Exception e){
            log.error(e.getMessage());
        }

        return responseData;
    }

    @Data
    static class UpdateMemberRequest{
        private String name;
        private String password;
        private String email;
        private String profileUrl;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse{
        private Long id;
        private String name;
        private String password;
        private String email;
        private String profileUrl;
    }

    /**
     * 회원 삭제
     */
    @ApiOperation(value = "", notes = "회원 정보 삭제")
    @DeleteMapping("/{id}")
    public ResponseData<DeleteMemberDto> deleteMember(@PathVariable("id") Long id){
        ResponseData<DeleteMemberDto> responseData = null;
        try{
            memberService.deleteById(id);
            responseData = new ResponseData<>(StatusCode.OK,ResponseMessage.SUCCESS,new DeleteMemberDto(id));
        }catch(Exception e){
            responseData = new ResponseData<>(StatusCode.BAD_REQUEST,ResponseMessage.NOT_FOUND_USER, new DeleteMemberDto(id));
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
