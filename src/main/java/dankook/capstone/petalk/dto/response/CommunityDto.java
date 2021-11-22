package dankook.capstone.petalk.dto.response;

import dankook.capstone.petalk.api.CommunityController;
import dankook.capstone.petalk.entity.Community;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
@AllArgsConstructor
public class CommunityDto {
    private Long id;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private String writer;
    private String title;
    private String content;
    private List<CommentDto> commentList;

    public CommunityDto(Community community){
        this.createdDate = community.getCreatedDate();
        this.lastModifiedDate = community.getLastModifiedDate();
        this.id = community.getId();
        this.writer = community.getMember().getNickname();
        this.title = community.getTitle();
        this.content = community.getContent();
        this.commentList = community.getCommentList().stream()
                .map(comment -> new CommentDto(comment))
                .collect(toList());
    }
}