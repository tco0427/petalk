package dankook.capstone.petalk.domain;

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

    private File profile;
    @OneToMany(mappedBy = "member")
    private List<Pet> petList=new ArrayList<>();
}
