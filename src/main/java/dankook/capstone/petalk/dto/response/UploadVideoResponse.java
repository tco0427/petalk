package dankook.capstone.petalk.dto.response;

import dankook.capstone.petalk.domain.Emotion;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadVideoResponse {
    private Long id;
    private String fileUri;
}
