package dankook.capstone.petalk.controller;

import dankook.capstone.petalk.domain.Member;
import dankook.capstone.petalk.domain.Pet;
import dankook.capstone.petalk.service.MemberService;
import dankook.capstone.petalk.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PetController {
    private final PetService petService;

    @GetMapping("pets/new")
    public String createForm(Model model){
        model.addAttribute("petForm",new PetForm());
        return "pets/createPetForm";
    }

    @PostMapping("pets/new")
    public String create(@Valid PetForm petForm, BindingResult result){
        if(result.hasErrors()){
            return "pets/createPetForm";
        }
        Pet pet=new Pet();
        pet.setPetName(petForm.getPetName());
        pet.setGender(petForm.getGender());
        pet.setPetType(petForm.getPetType());
        pet.setPetAge(petForm.getPetAge());

        /**
         * 여기서부터
         */
        Member member=new Member();
        member.setId("tco1498");
        pet.setMember(member);
        /**
         * 여기는 불필요한 부분
         */
        System.out.println(pet);
        petService.registration(pet);

        return "redirect:/";
    }

    @GetMapping("/pets")
    public String list(Model model){
        List<Pet> pets=petService.findPets();
        model.addAttribute("pets",pets);

        return "pets/petList";
    }
}
