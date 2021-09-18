package dankook.capstone.petalk.dto.request;

import lombok.Data;

@Data
public class UploadVideoRequest {
    private Long id;
    private Long memberId;
    private String fileUrl;
}
