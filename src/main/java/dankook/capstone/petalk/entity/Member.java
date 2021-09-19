package dankook.capstone.petalk.entity;

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

    private Integer platformId;

    @OneToMany(mappedBy = "member")
    private List<Pet> petList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Video> videoList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Community> communityList = new ArrayList<>();

    public Member(String name, String profileUrl, PlatformCode platformCode, Integer platformId) {
        this.name = name;
        this.profileUrl = profileUrl;
        this.platformCode = platformCode;
        this.platformId = platformId;
    }

    public Member(String userId, String password, String name, String nickname, String email, String profileUrl, PlatformCode platformCode, Integer platformId) {
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

    public void updateMember(String name, String password, String email, String profileUrl){
        if(name != null) {this.name = name;}

        if(password != null) {this.password = password;}

        if(email != null) {this.email = email;}

        if(profileUrl != null) {this.profileUrl = profileUrl;}
    }
}
