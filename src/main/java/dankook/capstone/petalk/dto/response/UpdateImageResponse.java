package dankook.capstone.petalk.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateImageResponse {
    private Long id;
    private String profileUrl;
}
