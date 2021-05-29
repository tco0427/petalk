package dankook.capstone.petalk.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@ToString
public class Pet {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="petpk")
    private Long id;

    @ManyToOne(fetch= LAZY)
    @JoinColumn(name="memberid")
    private Member member;

    @Column(name="petname")
    private String petName;

    @Column(name="sex")
    @Enumerated(EnumType.STRING)
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
}
