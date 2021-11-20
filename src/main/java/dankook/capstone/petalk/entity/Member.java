package dankook.capstone.petalk.entity;

import dankook.capstone.petalk.dto.response.OAuthResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
@ToString(of = {"id","userId","name","nickname","email"})
public class Member extends BaseEntity{

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String userId;

    private String password;

    private String name;

    private String nickname;

    private String email;

    private String profileUrl;

    @Enumerated(STRING)
    private PlatformCode platformCode;

    private String platformId;

    @OneToMany(mappedBy = "member")
    private List<Pet> petList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Video> videoList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Community> communityList = new ArrayList<>();

    public void setPassword(String password){
        this.password = password;
    }

    public Member(String name, String profileUrl, PlatformCode platformCode, String platformId) {
        this.name = name;
        this.profileUrl = profileUrl;
        this.platformCode = platformCode;
        this.platformId = platformId;
    }

    public Member(String userId, String password, String name, String nickname, String email, String profileUrl, PlatformCode platformCode, String platformId) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.profileUrl = profileUrl;
        this.platformCode = platformCode;
        this.platformId = platformId;
    }

    public Member(Long id, String userId, String password, String name, String nickname, String email, String profileUrl) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.profileUrl = profileUrl;
    }

    public Member(String userId, String password, String name, String nickname, String email, String profileUrl) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.profileUrl = profileUrl;
    }

    public Member(OAuthResponse oAuthResponse){
        this.platformId = oAuthResponse.getPlatformId();
        this.name = oAuthResponse.getNickname();
        this.nickname = oAuthResponse.getNickname();
        this.email = oAuthResponse.getEmail();
        this.profileUrl = oAuthResponse.getProfileImageUrl();
    }

    public void updateMember(String name, String nickname, String email){
        if(name != null) {this.name = name;}

        if(nickname != null) {this.nickname = nickname;}

        if(email != null) {this.email = email;}
    }

    public void updatePassword(String password) {
        if(password != null) {this.password = password;}
    }

    public void updateImage(String url) {
        if(url != null) {this.profileUrl = url;}
    }
}
