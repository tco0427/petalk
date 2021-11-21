package dankook.capstone.petalk.dto.response;

import dankook.capstone.petalk.entity.Comment;
import lombok.Data;

@Data
public class CommentDto {
    private String writer;
    private String content;

    public CommentDto(Comment comment){
        this.writer = comment.getMember().getNickname();
        this.content = comment.getContent();
    }
}
