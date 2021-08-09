package dankook.capstone.petalk.api;

import dankook.capstone.petalk.data.ResponseData;
import dankook.capstone.petalk.data.ResponseMessage;
import dankook.capstone.petalk.data.StatusCode;
import dankook.capstone.petalk.domain.Gender;
import dankook.capstone.petalk.domain.Member;
import dankook.capstone.petalk.domain.Pet;
import dankook.capstone.petalk.service.MemberService;
import dankook.capstone.petalk.service.PetService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RequestMapping("/api/pet")
@RestController
@RequiredArgsConstructor
@Slf4j
public class PetController {

    private final PetService petService;
    private final MemberService memberService;

    /**
     * 펫 정보 생성
     */

    @ApiOperation(value = "", notes = "신규 펫 정보 생성")
    @PostMapping("/new")
    public ResponseData<CreatePetResponse> saveMember(@RequestBody @Valid CreatePetRequest request) {
        ResponseData<CreatePetResponse> responseData;
        CreatePetResponse createPetResponse = null;

        try {
            Pet pet = new Pet();

            pet.setMember(request.getMember());
            pet.setPetName(request.getPetName());
            pet.setGender(request.getGender());
            pet.setPetType(request.getPetType());
            pet.setPetAge(request.getPetAge());

            Long id = petService.join(pet);

            createPetResponse = new CreatePetResponse(id,pet.getPetName(),pet.getGender(),pet.getPetType(),pet.getPetAge());

            responseData = new ResponseData<>(StatusCode.OK, ResponseMessage.SUCCESS, createPetResponse);

        }catch(Exception e){
            responseData = new ResponseData<>(StatusCode.BAD_REQUEST, ResponseMessage.PET_CREATION_FAIL,createPetResponse);
            log.error(e.getMessage());
        }
        return responseData;
    }

    @Data
    @AllArgsConstructor
    static class CreatePetResponse{
        private Long id;
        private String petName;
        private Gender gender;
        private String petType;
        private Integer petAge;
    }

    @Data
    static class CreatePetRequest{
        private Member member;
        private String petName;
        private Gender gender;
        private String petType;
        private Integer petAge;
    }

    /**
     * 펫 정보 조회
     */
    @ApiOperation(value = "", notes = "회원 정보로 펫 정보 조회")
    @GetMapping("/{id}")
    public ResponseData<List<PetDto>> getPetById(@ApiParam("회원 id") @PathVariable("id") Long id){
        log.info("getPetByMemberId : "+id);

        ResponseData<List<PetDto>> responseData = null;
        List<PetDto> petDtoList = null;

        try{
            Member findMember = memberService.findOne(id).get();
            List<Pet> petList = petService.findByMemberId(findMember.getId());

            petDtoList = petList.stream()
                    .map(p -> new PetDto(p))
                    .collect(Collectors.toList());

            responseData = new ResponseData<>(StatusCode.OK,ResponseMessage.SUCCESS,petDtoList);

        }catch(NoSuchElementException e){
            responseData = new ResponseData<>(StatusCode.BAD_REQUEST,ResponseMessage.NOT_FOUND_PET,petDtoList);
            log.error("Optional Error" + e.getMessage());
        }
        return responseData;
    }

    @Getter
    static class PetDto{
        private final Member member;
        private final String petName;

        public PetDto(Pet pet){
            member = pet.getMember();
            petName = pet.getPetName();
        }
    }

    /**
     * 펫 정보 수정
     */
    @ApiOperation(value = "", notes = "펫 정보 수정")
    @PutMapping("/{id}")
    public ResponseData<UpdatePetResponse> updatePet(@PathVariable("id") Long id,
                                                     @RequestBody @Valid UpdatePetRequest request){
        ResponseData<UpdatePetResponse> responseData = null;
        UpdatePetResponse updatePetResponse = null;
        try{
            petService.update(id,request.getPetName(),request.getGender(),request.getPetType(),request.getPetAge());
            Pet pet = petService.findOne(id).get();

            updatePetResponse = new UpdatePetResponse(pet.getId(),pet.getPetName(),pet.getGender(),pet.getPetType(),pet.getPetAge());
            responseData = new ResponseData<>(StatusCode.OK,ResponseMessage.SUCCESS,updatePetResponse);
        }catch(NoSuchElementException e){
            responseData = new ResponseData<>(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_PET,updatePetResponse);
            log.error(e.getMessage());
        }catch(Exception e){
            log.error(e.getMessage());
        }
        return responseData;
    }

    @Data
    static class UpdatePetRequest{
        private String petName;
        private Gender gender;
        private String petType;
        private Integer petAge;
    }

    @Data
    @AllArgsConstructor
    static class UpdatePetResponse{
        private Long id;
        private String petName;
        private Gender gender;
        private String petType;
        private Integer petAge;
    }


    /**
     * 펫 정보 삭제
     */
    @ApiOperation(value = "", notes = "펫 정보 삭제")
    @DeleteMapping("/{id}")
    public ResponseData<DeletePetDto> deletePet(@PathVariable("id") Long id){
        ResponseData<DeletePetDto> responseData = null;
        try{
            petService.deleteById(id);
            responseData = new ResponseData<>(StatusCode.OK, ResponseMessage.SUCCESS,new DeletePetDto(id));
        }catch(Exception e){
            responseData = new ResponseData<>(StatusCode.BAD_REQUEST,ResponseMessage.NOT_FOUND_PET,new DeletePetDto(id));
            log.error(e.getMessage());
        }
        return responseData;
    }


    @Data
    @AllArgsConstructor
    static class DeletePetDto{
        private Long id;
    }
}
