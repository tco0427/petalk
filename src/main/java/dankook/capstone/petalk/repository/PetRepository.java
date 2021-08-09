package dankook.capstone.petalk.repository;

import dankook.capstone.petalk.domain.Member;
import dankook.capstone.petalk.domain.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet,Long> {
}
