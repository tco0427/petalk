package dankook.capstone.petalk.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.File;
import java.sql.Date;

@Entity
@Getter @Setter
public class Community {
    @Id @GeneratedValue
    @Column(name="communitypk")
    private Long id;

    @ManyToOne
    @JoinColumn(name="memberid")
    private Member member;

    private String title;

    private String content;

    @Column(name="reportingdate")
    private Date date;

    private File attachment;

}
