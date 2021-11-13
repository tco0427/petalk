package dankook.capstone.petalk.dto.response;

import dankook.capstone.petalk.entity.Member;
import dankook.capstone.petalk.entity.Pet;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MemberDto {
    private String userId;
    private String nickname;
    private String name;
    private String email;
    private String token;

    public MemberDto(Member member){
        this.userId = member.getUserId();
        this.nickname = member.getNickname();
        this.name = member.getName();
        this.email = member.getEmail();
    }

    public MemberDto(Member member, String token){
        this.userId = member.getUserId();
        this.nickname = member.getNickname();
        this.name = member.getName();
        this.email = member.getEmail();
        this.token = token;
    }
}
