package dankook.capstone.petalk.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.File;

@Entity
@Getter @Setter
public class Member {
    @Id
    private String id;

    private String password;

    private String name;

    private String email;

    private File profile;
}
