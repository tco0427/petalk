package dankook.capstone.petalk.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
public class Community extends BaseEntity{
    @Id @GeneratedValue
    @Column(name="communitypk")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="memberid")
    private Member member;

//    private String writer;

    private String title;

    private String content;

    @OneToMany(mappedBy = "community")
    private List<Comment> commentList=new ArrayList<>();
}
