package dankook.capstone.petalk.dto.response;

import dankook.capstone.petalk.entity.Comment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private Long memberId;
    private String nickname;
    private String profileUrl;
    private String writer;
    private LocalDateTime createdDate;
    private String content;

    public CommentDto(Comment comment){
        this.memberId = comment.getMember().getId();
        this.nickname = comment.getMember().getNickname();
        this.profileUrl = comment.getMember().getProfileUrl();
        this.createdDate = comment.getCreatedDate();
        this.writer = comment.getMember().getNickname();
        this.content = comment.getContent();
    }
}
