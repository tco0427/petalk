package dankook.capstone.petalk.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
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
