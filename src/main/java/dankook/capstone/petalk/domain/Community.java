package dankook.capstone.petalk.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Community extends BaseEntity{
    @Id @GeneratedValue
    @Column(name="communitypk")
    private Long id;

    @ManyToOne
    @JoinColumn(name="memberid")
    private Member member;

    private String writer;

    private String title;

    private String content;

    @Column(name="reportingdate")
    private Date date;

    private File attachment;

    @OneToMany(mappedBy = "community")
    private List<Comment> commentList=new ArrayList<>();
}
