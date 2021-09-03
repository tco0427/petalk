package dankook.capstone.petalk.api;

import dankook.capstone.petalk.data.ResponseData;
import dankook.capstone.petalk.data.ResponseMessage;
import dankook.capstone.petalk.data.StatusCode;
import dankook.capstone.petalk.domain.Member;
import dankook.capstone.petalk.domain.Pet;
import dankook.capstone.petalk.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

            Member member = new Member(request.getUserId(),request.getName(),
                    request.getNickname(),request.getPassword(),request.getProfileUrl(),request.getEmail());

            Long id = memberService.join(member);

            createMemberResponse = new CreateMemberResponse(id, member.getUserId(), member.getName(), member.getEmail(),member.getNickname());
            responseData = new ResponseData<>(StatusCode.OK, ResponseMessage.SUCCESS, createMemberResponse);

        }catch(NoSuchElementException e){
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
        private String nickname;
        private String name;
        private String email;
    }

    @Data
    static class CreateMemberRequest{
        private String userId;
        private String nickname;
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
    public ResponseData<MemberDto> getMemberById(@PathVariable("id") Long id){
        log.info("getMemberById : " + id);

        ResponseData<MemberDto> responseData = null;

        MemberDto memberDto;

        try{
            Member member = memberService.findOne(id);
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

    @Data
    @AllArgsConstructor
    static class MemberDto{
        private String userId;
        private String nickname;
        private String name;
        private String email;
        private List<Pet> petList;
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
            Member member = memberService.findOne(id);

            updateMemberResponse = new UpdateMemberResponse(member.getId(),member.getName(),member.getPassword(),member.getEmail(),member.getProfileUrl(),member.getNickname());
            responseData = new ResponseData<>(StatusCode.OK,ResponseMessage.SUCCESS,updateMemberResponse);
        }catch(NoSuchElementException e){
            responseData = new ResponseData<>(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER, null);
        }catch(Exception e){
            log.error(e.getMessage());
        }

        return responseData;
    }

    @Data
    static class UpdateMemberRequest{
        private String name;
        private String nickname;
        private String password;
        private String email;
        private String profileUrl;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse{
        private Long id;
        private String name;
        private String nickname;
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
        }catch(NoSuchElementException e){
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
