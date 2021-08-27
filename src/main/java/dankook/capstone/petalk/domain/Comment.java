package dankook.capstone.petalk.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter @Setter
public class Comment extends BaseEntity{
    @Id @GeneratedValue
    @Column(name="commentpk")
    private Long id;

    @OneToOne
    @JoinColumn(name="memberid")
    private Member member;

    private String writer;

    private String content;

    @ManyToOne
    @JoinColumn(name="communitypk")
    private Community community;
}
