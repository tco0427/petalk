package dankook.capstone.petalk.dto.request;

import lombok.Data;

@Data
public class CreateCommunityRequest {
    private String title;
    private String content;
}
