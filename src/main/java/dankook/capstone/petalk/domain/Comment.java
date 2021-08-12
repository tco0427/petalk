package dankook.capstone.petalk.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter @Setter
public class Comment {
    @Id @GeneratedValue
    @Column(name="commentpk")
    private Long id;

    @OneToOne
    @JoinColumn(name="memberid")
    private Member member;

    @ManyToOne
    @JoinColumn(name="communitypk")
    private Community community;

    private Date date;

    private String content;
}
