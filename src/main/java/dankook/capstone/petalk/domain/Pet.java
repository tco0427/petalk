package dankook.capstone.petalk.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@ToString(of = {"id", "petName", "gender", "petType", "petAge"})
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
public class Pet {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="petpk")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch= LAZY)
    @JoinColumn(name="memberid")
    private Member member;

    @Column(name="petname")
    private String petName;

    @Column(name="gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name="pettype")
    private String petType;

    @Column(name="petage")
    private Integer petAge;

    @OneToMany(mappedBy = "pet")
    private List<Video> videoList = new ArrayList<>();

    //==연관관계 메소드==//
    public void setMember(Member member){
        this.member=member;
        member.getPetList().add(this);
    }

    public Pet(Long id, Member member, String petName, Gender gender, String petType, Integer petAge) {
        this.id = id;
        this.member = member;
        this.petName = petName;
        this.gender = gender;
        this.petType = petType;
        this.petAge = petAge;
    }

    public Pet(Member member, String petName, Gender gender, String petType, Integer petAge) {
        this.member = member;
        this.petName = petName;
        this.gender = gender;
        this.petType = petType;
        this.petAge = petAge;
    }

    public void update(String petName, Gender gender, String petType, Integer petAge) {
        this.petName = petName;
        this.gender = gender;
        this.petType = petType;
        this.petAge = petAge;
    }
}
