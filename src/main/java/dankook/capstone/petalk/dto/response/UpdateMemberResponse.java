package dankook.capstone.petalk.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateMemberResponse {
    private Long id;
    private String name;
    private String nickname;
    private String password;
    private String email;
    private String profileUrl;
}
