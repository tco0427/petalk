package dankook.capstone.petalk.api;

import dankook.capstone.petalk.data.ResponseData;
import dankook.capstone.petalk.data.ResponseMessage;
import dankook.capstone.petalk.data.StatusCode;
import dankook.capstone.petalk.domain.Comment;
import dankook.capstone.petalk.domain.Community;
import dankook.capstone.petalk.domain.Member;
import dankook.capstone.petalk.service.CommentService;
import dankook.capstone.petalk.service.CommunityService;
import dankook.capstone.petalk.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;
    private final MemberService memberService;
    private final CommunityService communityService;

    @ApiOperation(value = "", notes = "comment 생성")
    @PostMapping("/new")
    public ResponseData<CreateCommentResponse> createComment(@RequestBody @Valid CreateCommentRequest request){
        ResponseData<CreateCommentResponse> responseData = null;
        CreateCommentResponse createCommentResponse;

        try{
            Comment comment = new Comment();

            Member member = memberService.findOne(request.getMemberId());
            Community community = communityService.findOne(request.getCommunityId());
            String content = request.getContent();

            comment.setMember(member);
            comment.setCommunity(community);
            comment.setContent(content);

            Long id = commentService.register(comment);

            createCommentResponse = new CreateCommentResponse(id, community.getId(), member.getNickname(), content);
            responseData = new ResponseData<>(StatusCode.OK, ResponseMessage.SUCCESS, createCommentResponse);
        }catch(NoSuchElementException e){
            responseData = new ResponseData<>(StatusCode.UNAUTHORIZED, ResponseMessage.COMMENT_CREATION_FAIL, null);
            log.error(e.getMessage());
        }catch(Exception e){
            log.error(e.getMessage());
        }

        return responseData;
    }

    @Data
    @AllArgsConstructor
    static class CreateCommentResponse{
        private Long id;
        private Long communityId;
        private String writer;
        private String content;
    }

    @Data
    static class CreateCommentRequest{
        private Long id;
        private Long memberId;
        private Long communityId;
        private String content;
    }

    @ApiOperation(value = "", notes = "댓글 삭제")
    @DeleteMapping("/{id}")
    public ResponseData<DeleteCommentDto> deleteComment(@PathVariable("id") Long id){
        ResponseData<DeleteCommentDto> responseData = null;

        try{
            commentService.deleteById(id);
            responseData = new ResponseData<>(StatusCode.OK, ResponseMessage.SUCCESS, new DeleteCommentDto(id));
        }catch(NoSuchElementException e){
            responseData = new ResponseData<>(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_COMMENT, null);
        }

        return responseData;
    }

    @Data
    @AllArgsConstructor
    static class DeleteCommentDto{
        private Long id;
    }
}
