package hansung.ElderCare.Server.controller.specification;

import hansung.ElderCare.Server.apiPayload.ApiResponse;
import hansung.ElderCare.Server.dto.ProtectedDTO.ProtectedRequestDTO;
import hansung.ElderCare.Server.dto.ProtectedDTO.ProtectedResponseDTO;
import hansung.ElderCare.Server.dto.UserDTO.UserRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

public interface ProtectedSpecification {

    @PostMapping
    @Operation(summary = "보호대상자 등록", description = "보호대상자 기본 정보를 등록합니다.")
    public ApiResponse<Long> registerProtected(ProtectedRequestDTO.RegistrationDTO registrationDTO);

    @GetMapping
    @Operation(summary = "보호대상자 조회", description = "보호대상자 정보를 조회합니다.")
    public ApiResponse<ProtectedResponseDTO.ProtectedInfo> getProtected();

    @GetMapping
    @Operation(summary = "보호대상자 연락하기", description = "보호대상자의 전화번호를 받아 반환")
    public ApiResponse<ProtectedResponseDTO.protectedPhoneNumber> getPhoneNumber();
}
