package dankook.capstone.petalk.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
public class Member extends BaseEntity{

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String userId;

    private String password;

    private String name;

    private String nickname;

    private String email;

    private String profileUrl;

    @OneToMany(mappedBy = "member")
    private List<Pet> petList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Video> videoList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Community> communityList = new ArrayList<>();
}
