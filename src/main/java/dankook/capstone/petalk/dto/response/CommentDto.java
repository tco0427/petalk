package dankook.capstone.petalk.dto.response;

import dankook.capstone.petalk.entity.Comment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private String writer;
    private LocalDateTime createdDate;
    private String content;

    public CommentDto(Comment comment){
        this.createdDate = comment.getCreatedDate();
        this.writer = comment.getMember().getNickname();
        this.content = comment.getContent();
    }
}
