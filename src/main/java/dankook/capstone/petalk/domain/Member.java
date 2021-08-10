package dankook.capstone.petalk.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.*;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String userId;

    private String password;

    private String name;

    private String email;

    private String profileUrl;

    private Integer createdAt;

    private Integer updatedAt;

    @OneToMany(mappedBy = "member")
    private List<Pet> petList=new ArrayList<>();
}
