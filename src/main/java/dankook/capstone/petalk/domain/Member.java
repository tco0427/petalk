package dankook.capstone.petalk.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id
    private String id;

    private String password;

    private String name;

    private String email;

    private File profile;
    @OneToMany(mappedBy = "member")
    private List<Pet> petList=new ArrayList<>();
}
