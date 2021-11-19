package dankook.capstone.petalk.dto.request;

import lombok.Data;

@Data
public class CreateCommentRequest {
    private Long communityId;
    private String content;
}
