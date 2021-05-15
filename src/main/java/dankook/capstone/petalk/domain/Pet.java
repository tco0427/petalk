package dankook.capstone.petalk.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Pet {
    @Id @GeneratedValue
    @Column(name="petPk")
    private Long id;

    @ManyToOne
    @JoinColumn(name="memberid")
    private Member member;
    private String petName;
    private String sex;
    private String petType;
    private Long petAge;
}
