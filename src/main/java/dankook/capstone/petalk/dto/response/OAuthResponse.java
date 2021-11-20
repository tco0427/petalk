package dankook.capstone.petalk.dto.response;

import dankook.capstone.petalk.entity.PlatformType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OAuthResponse {
    private PlatformType platformType;

    private String platformId;

    private String nickname;

    private String profileImageUrl;

    private String email;
}
