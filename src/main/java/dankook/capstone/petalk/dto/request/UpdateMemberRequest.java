package dankook.capstone.petalk.dto.request;

import lombok.Data;

@Data
public class UpdateMemberRequest {
    private String name;
    private String nickname;
    private String email;
}
