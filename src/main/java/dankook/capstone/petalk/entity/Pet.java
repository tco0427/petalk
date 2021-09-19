package dankook.capstone.petalk.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import static javax.persistence.EnumType.*;
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

    @ManyToOne(fetch= LAZY)
    @JoinColumn(name="memberid")
    private Member member;

    @Column(name="petname")
    private String petName;

    @Column(name="gender")
    @Enumerated(STRING)
    private Gender gender;

    @Column(name="pettype")
    private String petType;

    @Column(name="petage")
    private Integer petAge;

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
