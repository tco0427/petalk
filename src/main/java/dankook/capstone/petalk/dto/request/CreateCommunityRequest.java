package dankook.capstone.petalk.dto.request;

import lombok.Data;

@Data
public class CreateCommunityRequest {
    private Long memberId;
    private String title;
    private String content;
}
