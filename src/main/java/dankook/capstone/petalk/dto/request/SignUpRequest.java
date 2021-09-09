package dankook.capstone.petalk.dto.request;

import dankook.capstone.petalk.domain.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SignUpRequest {
    private String name;
    private String profileUrl;
    private String platformCode;
    private Integer platformId;

    public Member toMemberEntity(){
        return new Member(this.name, this.profileUrl, this.platformCode, this.platformId);
    }
}
