package dankook.capstone.petalk.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateCommentResponse {
    private Long id;
    private Long communityId;
    private String writer;
    private String content;
}
