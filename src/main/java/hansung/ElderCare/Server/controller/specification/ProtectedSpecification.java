package hansung.ElderCare.Server.controller.specification;

import hansung.ElderCare.Server.apiPayload.ApiResponse;
import hansung.ElderCare.Server.dto.ProtectedDTO.ProtectedRequestDTO;
import hansung.ElderCare.Server.dto.ProtectedDTO.ProtectedResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;

public interface ProtectedSpecification {

    @Operation(summary = "보호대상자 등록", description = "보호대상자의 데이터를 받아 저장합니다.")
    @PostMapping(value = "/registration", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<?> registerProtected(
            @RequestPart(value = "request") @Valid @Parameter(description = "보호대상자 정보(JSON)") ProtectedRequestDTO.RegistrationDTO request,
            @RequestPart(value = "image", required = false) @Parameter(description = "보호대상자 프로필 이미지") MultipartFile image,
            BindingResult bindingResult
    );

    @Operation(summary = "보호대상자 정보 수정", description = "보호대상자의 데이터를 받아 수정합니다.")
    @PatchMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<?> updateProtected(
            @RequestPart(value = "request") @Valid @Parameter(description = "보호대상자 정보(JSON)") ProtectedRequestDTO.RegistrationDTO request,
            @RequestPart(value = "image", required = false) @Parameter(description = "보호대상자 프로필 이미지") MultipartFile image,
            BindingResult bindingResult
    );

    @GetMapping
    @Operation(summary = "보호대상자 조회", description = "보호대상자 정보를 조회합니다.")
    public ApiResponse<ProtectedResponseDTO.ProtectedInfo> getProtected();

    @GetMapping
    @Operation(summary = "보호대상자 연락하기", description = "보호대상자의 전화번호를 받아 반환")
    public ApiResponse<ProtectedResponseDTO.protectedPhoneNumber> getPhoneNumber();

    @PostMapping
    @Operation(summary = "보호대상자 건강정보 등록하기", description = "보호대상자 건강정보 데이터를 받아 저장")
    public ApiResponse<?> registerHealthInfo(ProtectedRequestDTO.ProtectedHealthInfo request, BindingResult bindingResult);

    @GetMapping
    @Operation(summary = "보호대상자 건강정보 조회", description = "보호대상자 건강정보 조회")
    public ApiResponse<?> getHealthInfo();

    @PutMapping
    @Operation(summary = "보호대상자 키, 몸무게 수정", description = "보호대상자 키, 몸무게 수정")
    public ApiResponse<?> updateHeightWeight(ProtectedRequestDTO.HeightWeightDTO request, BindingResult bindingResult);

    @PutMapping
    @Operation(summary = "보호대상자 혈액형 수정", description = "보호대상자 혈액형 수정")
    public ApiResponse<?> updateBloodType(ProtectedRequestDTO.BloodTypeDTO request, BindingResult bindingResult);

    @PutMapping
    @Operation(summary = "보호대상자 알레르기 수정", description = "보호대상자 알레르기 수정")
    public ApiResponse<?> updateAllergy(ProtectedRequestDTO.AllergiesDTO request, BindingResult bindingResult);

    @PutMapping
    @Operation(summary = "보호대상자 백신이력 수정", description = "보호대상자 백신이력 수정")
    public ApiResponse<?> updateVaccine(ProtectedRequestDTO.VaccinesDTO request, BindingResult bindingResult);

    @PutMapping
    @Operation(summary = "보호대상자 수술이력 수정", description = "보호대상자 수술이력 수정")
    public ApiResponse<?> updateSurgery(ProtectedRequestDTO.SurgeriesDTO request, BindingResult bindingResult);
}
