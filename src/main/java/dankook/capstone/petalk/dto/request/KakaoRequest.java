package dankook.capstone.petalk.dto.request;

import dankook.capstone.petalk.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KakaoRequest {
    private Integer platformId;
    private String nickname;
    private String profileUrl;
    private String email;

    public Member toMemberEntity() {
        return new Member(platformId, nickname, profileUrl, email);
    }
}