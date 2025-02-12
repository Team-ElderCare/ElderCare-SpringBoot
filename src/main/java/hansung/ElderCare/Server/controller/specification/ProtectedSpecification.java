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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

public interface ProtectedSpecification {

    @Operation(summary = "보호대상자 등록", description = "보호대상자의 데이터를 받아 저장합니다.")
    @PostMapping(value = "/registration", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<?> registerProtected(
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
}
