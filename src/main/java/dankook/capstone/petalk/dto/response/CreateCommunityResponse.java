package dankook.capstone.petalk.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateCommunityResponse {
    private Long id;
    private Long memberId;
    private String writer;
    private String title;
}
