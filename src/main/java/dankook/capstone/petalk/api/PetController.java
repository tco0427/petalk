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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

        }catch(IllegalStateException e){
            responseData = new ResponseData<>(StatusCode.BAD_REQUEST, ResponseMessage.MEMBER_CREATION_FAIL, createPetResponse);
            log.error(e.getMessage());
        }catch(Exception e){
            responseData = new ResponseData<>(StatusCode.BAD_REQUEST, ResponseMessage.MEMBER_CREATION_FAIL,createPetResponse);
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

    /**
     * 펫 정보 수정
     */

    /**
     * 펫 정보 삭제
     */
}
