package dankook.capstone.petalk.api;

import dankook.capstone.petalk.data.ResponseData;
import dankook.capstone.petalk.data.ResponseMessage;
import dankook.capstone.petalk.data.StatusCode;
import dankook.capstone.petalk.entity.Gender;
import dankook.capstone.petalk.entity.Member;
import dankook.capstone.petalk.entity.Pet;
import dankook.capstone.petalk.dto.request.CreatePetRequest;
import dankook.capstone.petalk.dto.request.UpdatePetRequest;
import dankook.capstone.petalk.dto.response.CreatePetResponse;
import dankook.capstone.petalk.dto.response.UpdatePetResponse;
import dankook.capstone.petalk.service.MemberService;
import dankook.capstone.petalk.service.PetService;
import io.swagger.annotations.ApiOperation;
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
            Member member = memberService.findOne(request.getMemberId());

            Pet pet = new Pet(member, request.getPetName(), request.getGender(), request.getPetType(), request.getPetAge());

            Long id = petService.join(pet);

            createPetResponse = new CreatePetResponse(id,pet.getPetName(),pet.getGender(),pet.getPetType(),pet.getPetAge());

            responseData = new ResponseData<>(StatusCode.OK, ResponseMessage.SUCCESS, createPetResponse);

        }catch(IllegalArgumentException e){
            responseData = new ResponseData<>(StatusCode.BAD_REQUEST, ResponseMessage.PET_CREATION_FAIL, null);
            log.error(e.getMessage());
        }catch(Exception e){
            responseData = new ResponseData<>(StatusCode.BAD_REQUEST, ResponseMessage.PET_CREATION_FAIL, null);
            log.error(e.getMessage());
        }
        return responseData;
    }

    /**
     * 펫 정보 조회
     */
    @ApiOperation(value = "", notes = "회원 정보로 펫 정보 조회")
    @GetMapping("/{id}")
    public ResponseData<PetListDto> getPetById(@PathVariable("id") Long id){
        log.info("getPetByMemberId : "+id);

        ResponseData<PetListDto> responseData;
        PetListDto petListDto;

        try{
            Member findMember = memberService.findOne(id);

            List<Pet> petList = findMember.getPetList();

            List<PetDto> petDtoList = petList.stream()
                    .map(p -> new PetDto(p))
                    .collect(Collectors.toList());

            petListDto = new PetListDto(findMember.getId(), petDtoList);

            responseData = new ResponseData<>(StatusCode.OK,ResponseMessage.SUCCESS,petListDto);

        }catch(NoSuchElementException e){
            responseData = new ResponseData<>(StatusCode.BAD_REQUEST,ResponseMessage.NOT_FOUND_PET, null);
            log.error("Optional Error" + e.getMessage());
        }
        return responseData;
    }

    @Getter
    static class PetDto{
        private Long id;
        private String petName;
        private Gender gender;
        private String petType;
        private Integer petAge;

        public PetDto(Pet pet){
            id = pet.getId();
            petName = pet.getPetName();
            gender = pet.getGender();
            petType = pet.getPetType();
            petAge = pet.getPetAge();
        }
    }

    @Getter
    static class PetListDto{
        private final Long memberId;
        private final List<PetDto> petList;

        public PetListDto(Long memberId, List<PetDto> petList){
            this.memberId = memberId;
            this.petList = petList;
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
        UpdatePetResponse updatePetResponse;
        try{
            petService.update(id,request.getPetName(),request.getGender(),request.getPetType(),request.getPetAge());
            Pet pet = petService.findOne(id);

            updatePetResponse = new UpdatePetResponse(pet.getId(),pet.getPetName(),pet.getGender(),pet.getPetType(),pet.getPetAge());
            responseData = new ResponseData<>(StatusCode.OK,ResponseMessage.SUCCESS,updatePetResponse);
        }catch(NoSuchElementException e){
            responseData = new ResponseData<>(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_PET, null);
            log.error(e.getMessage());
        }catch(Exception e){
            log.error(e.getMessage());
        }
        return responseData;
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
        }catch(NoSuchElementException e){
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
