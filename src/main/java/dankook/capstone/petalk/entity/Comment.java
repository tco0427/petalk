package dankook.capstone.petalk.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
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

    private String content;

    @ManyToOne
    @JoinColumn(name="communitypk")
    private Community community;

    public Comment(Member member, String content, Community community) {
        this.member = member;
        this.content = content;
        this.community = community;
    }

    public void setContent(String content){
        this.content = content;
    }
}
