package dankook.capstone.petalk.api;

import dankook.capstone.petalk.data.ResponseData;
import dankook.capstone.petalk.data.ResponseMessage;
import dankook.capstone.petalk.data.StatusCode;
import dankook.capstone.petalk.domain.Comment;
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
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

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
        ResponseData<CreateCommunityResponse> responseData;
        CreateCommunityResponse createCommunityResponse;

        try{
            Community community = new Community();

            Member findMember = memberService.findOne(request.getMemberId());

            String nickname = findMember.getNickname();

            community.setTitle(request.getTitle());
            community.setWriter(nickname);
            community.setContent(request.getContent());
            community.setMember(findMember);

            Long id = communityService.register(community);

            createCommunityResponse = new CreateCommunityResponse(id, findMember.getId(),community.getWriter(), community.getTitle());
            responseData = new ResponseData<>(StatusCode.OK, ResponseMessage.SUCCESS, createCommunityResponse);
        }catch(NoSuchElementException e){
            responseData = new ResponseData<>(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER, null);
            log.error("Can't find Member");
        }catch(Exception e){
            responseData = new ResponseData<>(StatusCode.BAD_REQUEST, ResponseMessage.COMMUNITY_CREATION_FAIL, null);
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
    }

    @Data
    static class CreateCommunityRequest{
        private Long memberId;
        private String title;
        private String content;
    }

    /**
     * 게시글 내용 수정
     */

    @ApiOperation(value = "", notes = "게시글 수정")
    @PutMapping("/{id}")
    public ResponseData<UpdateCommunityResponse> updateCommunity(@PathVariable("id") Long id,
                                                                 @RequestBody @Valid UpdateCommunityRequest request){
        ResponseData<UpdateCommunityResponse> responseData = null;
        UpdateCommunityResponse updateCommunityResponse;

        try{
            communityService.update(id,request.getContent());

            Community community = communityService.findOne(id);

            updateCommunityResponse = new UpdateCommunityResponse(id,community.getMember().getId(),community.getTitle(),community.getContent());

            responseData = new ResponseData<>(StatusCode.OK, ResponseMessage.SUCCESS,updateCommunityResponse);
        }catch(NoSuchElementException e){
            responseData = new ResponseData<>(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_COMMUNITY, null);
            log.error(e.getMessage());
        }catch(Exception e){
            log.error(e.getMessage());
        }

        return responseData;
    }

    @Data
    static class UpdateCommunityRequest{
        private String content;
    }

    @Data
    @AllArgsConstructor
    static class UpdateCommunityResponse{
        private Long id;
        private Long memberId;
        private String title;
        private String content;
    }

    /**
     * 게시글 삭제
     */
    @ApiOperation(value = "", notes = "게시글 삭제")
    @DeleteMapping("/{id}")
    public ResponseData<DeleteCommunityDto> deleteCommunity(@PathVariable("id") Long id){
        ResponseData<DeleteCommunityDto> responseData = null;

        try{
            communityService.deleteById(id);
            responseData = new ResponseData<>(StatusCode.OK,ResponseMessage.SUCCESS,new DeleteCommunityDto(id));
        }catch(NoSuchElementException e){
            responseData = new ResponseData<>(StatusCode.NOT_FOUND,ResponseMessage.NOT_FOUND_COMMUNITY,new DeleteCommunityDto(id));
        }

        return  responseData;
    }

    @Data
    @AllArgsConstructor
    static class DeleteCommunityDto{
        private Long id;
    }

    /**
     * 게시글 조회(검색)
     */
    @ApiOperation(value = "", notes = "게시글 검색")
    @GetMapping("/{id}")
    public ResponseData<CommunityDto> getCommunity(@PathVariable("id") Long id){
        ResponseData<CommunityDto> responseData = null;
        CommunityDto communityDto = null;

        try{
            Community community = communityService.findOne(id);
            communityDto = new CommunityDto(community);

            responseData = new ResponseData<>(StatusCode.OK, ResponseMessage.SUCCESS, communityDto);
        }catch(NoSuchElementException e){
            responseData = new ResponseData<>(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_COMMUNITY, null);
        }catch(Exception e){
            log.error(e.getMessage());
        }
        return responseData;
    }

    @Data
    static class CommunityDto{
        private Long id;
        private String writer;
        private String title;
        private String content;
        private List<CommentDto> commentList;

        public CommunityDto(Community community){
            this.id = community.getId();
            this.writer = community.getWriter();
            this.title = community.getTitle();
            this.content = community.getContent();
            this.commentList = community.getCommentList().stream()
                    .map(comment -> new CommentDto(comment))
                    .collect(toList());
        }
    }

    @Data
    static class CommentDto{
        private String writer;
        private String content;

        public CommentDto(Comment comment){
            this.writer = comment.getMember().getNickname();
            this.content = comment.getContent();
        }
    }


    /**
     * 게시글 조회(페이징)
     */
}
