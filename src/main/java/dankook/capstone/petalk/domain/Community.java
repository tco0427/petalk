package dankook.capstone.petalk.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
public class Community extends BaseEntity{
    @Id @GeneratedValue
    @Column(name="communitypk")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="memberid")
    private Member member;

    private String title;

    private String content;

    @OneToMany(mappedBy = "community")
    private List<Comment> commentList=new ArrayList<>();
}
