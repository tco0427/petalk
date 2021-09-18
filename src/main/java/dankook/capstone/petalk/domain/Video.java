package dankook.capstone.petalk.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @JoinColumn(name="memberid")
    private Member member;

    private String fileUrl;

    @Enumerated(EnumType.STRING)
    private Emotion emotion;

    public Video(Member member, String fileUrl) {
        this.member = member;
        this.fileUrl = fileUrl;
    }

    public void setEmotion(Emotion emotion){
        this.emotion = emotion;
    }
}
