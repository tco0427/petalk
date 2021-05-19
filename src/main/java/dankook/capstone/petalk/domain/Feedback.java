package dankook.capstone.petalk.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Feedback {
    @Id @GeneratedValue
    @Column(name="feedbackpk")
    private Long id;

    @OneToOne
    @JoinColumn(name="videopk")
    private Video video;

    @OneToOne
    @JoinColumn(name="memberid")
    private Member member;

    @Enumerated(EnumType.STRING)
    private Emotion feedBackEmotion;

    private String etc;
}
