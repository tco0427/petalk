package dankook.capstone.petalk.repository;

import dankook.capstone.petalk.domain.Pet;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet,Long> {
    public List<Pet> findByMemberName(Long id);
}
