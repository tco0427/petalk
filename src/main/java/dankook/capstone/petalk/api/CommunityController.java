package dankook.capstone.petalk.api;

import dankook.capstone.petalk.data.ResponseData;
import dankook.capstone.petalk.data.ResponseMessage;
import dankook.capstone.petalk.data.StatusCode;
import dankook.capstone.petalk.domain.Community;
import dankook.capstone.petalk.domain.Member;
import dankook.capstone.petalk.service.CommunityService;
import dankook.capstone.petalk.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.sql.Date;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/community")
public class CommunityController {

    private final CommunityService  communityService;
    private final MemberService memberService;

    /**
     * 게시글 생성
     */
    @ApiOperation(value = "", notes = "새로운 게시글 생성")
    @PostMapping("/new")
    public ResponseData<CreateCommunityResponse> createCommunity(@RequestBody @Valid CreateCommunityRequest request){
        ResponseData<CreateCommunityResponse> responseData = null;
        CreateCommunityResponse createCommunityResponse = null;

        try{
            Community community = new Community();

            Member findMember = memberService.findOne(request.getMemberId()).get();

            String nickname = findMember.getNickname();

            community.setTitle(request.getTitle());
            community.setWriter(nickname);
            community.setContent(request.getContent());
            community.setMember(findMember);
            community.setDate(new Date(System.currentTimeMillis()));
            community.setAttachment(request.getAttachment());

            Long id = communityService.register(community);

            createCommunityResponse = new CreateCommunityResponse(id, findMember.getId(),community.getWriter(), community.getTitle(), community.getDate());
            responseData = new ResponseData<>(StatusCode.OK, ResponseMessage.SUCCESS, createCommunityResponse);
        }catch(NoSuchElementException e){
            responseData = new ResponseData<>(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER, createCommunityResponse);
            log.error("Can't find Member");
        }catch(Exception e){
            responseData = new ResponseData<>(StatusCode.BAD_REQUEST, ResponseMessage.COMMUNITY_CREATION_FAIL,createCommunityResponse);
            log.error(e.getMessage());
        }

        return responseData;
    }

    @Data
    @AllArgsConstructor
    static class CreateCommunityResponse{
        private Long id;
        private Long memberId;
        private String writer;
        private String title;
        private Date date;
    }

    @Data
    static class CreateCommunityRequest{
        private Long memberId;
        private String title;
        private String content;
        private File attachment;
    }

    /**
     * 게시글 내용 수정
     */

    @ApiOperation(value = "", notes = "게시글 수정")
    @PutMapping
    public ResponseData<UpdateCommunityResponse> updateCommunity(@PathVariable("id") Long id,
                                                                 @RequestBody @Valid UpdateCommunityRequest request){
        ResponseData<UpdateCommunityResponse> responseData = null;
        UpdateCommunityResponse updateCommunityResponse = null;

        try{
            communityService.update(id,request.getContent(),request.getAttachment());

            Community community = communityService.findOne(id).get();

            updateCommunityResponse = new UpdateCommunityResponse(id,community.getMember().getId(),community.getTitle(),community.getContent(),community.getAttachment());

            responseData = new ResponseData<>(StatusCode.OK,ResponseMessage.SUCCESS,updateCommunityResponse);
        }catch(NoSuchElementException e){
            responseData = new ResponseData<>(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_COMMUNITY, updateCommunityResponse);
        }catch(Exception e){
            log.error(e.getMessage());
        }

        return responseData;
    }

    @Data
    static class UpdateCommunityRequest{
        private String content;
        private File attachment;
    }

    @Data
    @AllArgsConstructor
    static class UpdateCommunityResponse{
        private Long id;
        private Long memberId;
        private String title;
        private String content;
        private File attachment;
    }

    /**
     * 게시글 삭제
     */


    /**
     * 게시글 조회(검색)
     */

    /**
     * 게시글 조회(페이징)
     */
}
