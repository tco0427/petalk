package dankook.capstone.petalk.repository;

import dankook.capstone.petalk.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet,Long> {
    public void deleteByPetName(String petName);
}
