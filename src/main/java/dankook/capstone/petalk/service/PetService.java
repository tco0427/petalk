package dankook.capstone.petalk.service;

import dankook.capstone.petalk.domain.Member;
import dankook.capstone.petalk.domain.Pet;
import dankook.capstone.petalk.repository.MemberRepository;
import dankook.capstone.petalk.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;

    @Transactional
    public Long registration(Pet pet){
        petRepository.save(pet);
        return pet.getId();
    }


    public List<Pet> findPets(){
        return petRepository.findAll();
    }

    public Pet findOne(Long petId){
        return petRepository.findOne(petId);
    }
}
