package dankook.capstone.petalk.dto.request;

import dankook.capstone.petalk.domain.Member;
import dankook.capstone.petalk.domain.PlatformCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class SignUpRequest {
    private String userId;
    private String password;
    private String nickname;
    private String name;
    private String email;
    private String profileUrl;
    private PlatformCode platformCode;
    private Integer platformId;

    public Member toMemberEntity() {
        return new Member(this.userId, this.password, this.name, this.nickname, this.email, this.profileUrl, this.platformCode, this.platformId);
    }
}
