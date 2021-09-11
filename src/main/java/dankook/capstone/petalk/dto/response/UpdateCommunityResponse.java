package dankook.capstone.petalk.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateCommunityResponse {
    private Long id;
    private Long memberId;
    private String title;
    private String content;
}
