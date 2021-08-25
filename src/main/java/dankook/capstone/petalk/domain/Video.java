package dankook.capstone.petalk.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.File;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Video extends BaseEntity{
    @Id @GeneratedValue
    @Column(name="videopk")
    private Long id;

    @ManyToOne(fetch= LAZY)
    @JoinColumn(name="petpk")
    private Pet pet;

    @ManyToOne(fetch= LAZY)
    @JoinColumn(name="memberid")
    private Member member;

    private File video;

    @Enumerated(EnumType.STRING)
    private Emotion emotion;
}
