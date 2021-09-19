package dankook.capstone.petalk.service;

import dankook.capstone.petalk.entity.Gender;
import dankook.capstone.petalk.entity.Pet;
import dankook.capstone.petalk.repository.MemberRepository;
import dankook.capstone.petalk.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Pet pet){
        petRepository.save(pet);
        return pet.getId();
    }


    public List<Pet> findPets(){
        return petRepository.findAll();
    }

    public Pet findOne(Long petId){
        return petRepository.findById(petId)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public void update(Long id, String petName, Gender gender, String petType, Integer petAge){
        Pet pet = petRepository.findById(id).get();

        pet.update(petName, gender, petType, petAge);
    }

    @Transactional
    public void deleteById(Long id){
        petRepository.deleteById(id);
    }
}
