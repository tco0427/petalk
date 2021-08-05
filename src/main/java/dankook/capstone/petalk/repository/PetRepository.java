package dankook.capstone.petalk.repository;

import dankook.capstone.petalk.domain.Pet;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet,Long> {
/*
    private final EntityManager em;

    public void save(Pet pet){
        em.persist(pet);
    }

    public Pet findOne(Long id){
        return em.find(Pet.class,id);
    }
    public List<Pet> findAll(){
        return em.createQuery("select p from Pet p", Pet.class)
                .getResultList();
    }

    public List<Pet> findById(String id){
        return em.createQuery("select p from Pet p where p.id=:id",Pet.class)
                .setParameter("id",id)
                .getResultList();
    }

*/
}
