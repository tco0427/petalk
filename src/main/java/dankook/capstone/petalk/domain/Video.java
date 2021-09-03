package dankook.capstone.petalk.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
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

    @Setter
    @Enumerated(EnumType.STRING)
    private Emotion emotion;

    public Video(Member member, Pet pet, String fileName, Long duration, Long size, String fileUri) {
        this.pet = pet;
        this.member = member;
        this.fileName = fileName;
        this.duration = duration;
        this.size = size;
        this.fileUri = fileUri;
    }
}
