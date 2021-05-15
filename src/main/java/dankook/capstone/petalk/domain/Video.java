package dankook.capstone.petalk.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.File;

@Entity
@Getter @Setter
public class Video {
    @Id @GeneratedValue
    @Column(name="videopk")
    private Long id;

    @OneToOne
    @JoinColumn(name="petpk")
    private Pet pet;

    @OneToOne
    @JoinColumn(name="memberid")
    private Member member;

    private File video;

    @Enumerated(EnumType.STRING)
    private Emotion emotion;
}
