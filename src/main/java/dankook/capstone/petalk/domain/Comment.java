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
public class Comment extends BaseEntity{
    @Id @GeneratedValue
    @Column(name="commentpk")
    private Long id;

    @OneToOne
    @JoinColumn(name="memberid")
    private Member member;

    @Setter
    private String content;

    @ManyToOne
    @JoinColumn(name="communitypk")
    private Community community;

    public Comment(Member member, String content, Community community) {
        this.member = member;
        this.content = content;
        this.community = community;
    }
}
