package dankook.capstone.petalk.service;

import dankook.capstone.petalk.domain.Gender;
import dankook.capstone.petalk.domain.Pet;
import dankook.capstone.petalk.repository.MemberRepository;
import dankook.capstone.petalk.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


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
        pet.setPetName(petName);
        pet.setGender(gender);
        pet.setPetType(petType);
        pet.setPetAge(petAge);
    }

    @Transactional
    public void deleteById(Long id){
        petRepository.deleteById(id);
    }

/*
    public List<Pet> findByMemberId(Long id){
        Member member = memberRepository.findById(id).get();
        List<Pet> pets = petRepository.findAll();

        List<Pet> result = new ArrayList<>();

        for (Pet pet : pets) {
            if(pet.getMember().getId() == member.getId()){
                result.add(pet);
            }
        }

        return result;
    }
*/
}
