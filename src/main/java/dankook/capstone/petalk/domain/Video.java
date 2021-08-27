package dankook.capstone.petalk.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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

    private String fileName;
    private Long duration;
    private Long size;
    private String fileUri;

    @Enumerated(EnumType.STRING)
    private Emotion emotion;
}
