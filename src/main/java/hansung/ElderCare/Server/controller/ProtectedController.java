package hansung.ElderCare.Server.controller;

import hansung.ElderCare.Server.apiPayload.ApiResponse;
import hansung.ElderCare.Server.apiPayload.code.status.ErrorStatus;
import hansung.ElderCare.Server.controller.specification.ProtectedSpecification;
import hansung.ElderCare.Server.dto.ProtectedDTO.ProtectedRequestDTO;
import hansung.ElderCare.Server.dto.ProtectedDTO.ProtectedResponseDTO;
import hansung.ElderCare.Server.dto.UserDTO.UserRequestDTO;
import hansung.ElderCare.Server.service.protectedService.ProtectedCommandService;
import hansung.ElderCare.Server.service.protectedService.ProtectedCommandServiceImpl;
import hansung.ElderCare.Server.service.protectedService.ProtectedQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Protected", description = "보호대상자 관련 API")
@RestController
@RequestMapping("/protected")
@RequiredArgsConstructor
public class ProtectedController implements ProtectedSpecification {

    private final ProtectedCommandServiceImpl protectedCommandService;
    private final ProtectedQueryService protectedQueryService;

    @Override
    @PostMapping("/registration")
    public ApiResponse<Long> registerProtected(@RequestBody @Valid ProtectedRequestDTO.RegistrationDTO request) {
        Long id = protectedCommandService.registrationProtected(request, 2L);
        return ApiResponse.onSuccess(id);
    }

    @Override
    @GetMapping
    public ApiResponse<ProtectedResponseDTO.ProtectedInfo> getProtected() {
        ProtectedResponseDTO.ProtectedInfo protectedInfo = protectedQueryService.getProtectedInfo(1L);
        if (protectedInfo == null) {
            return ApiResponse.onFailure(ErrorStatus.PROTECTED_NULL.getCode(), ErrorStatus.PROTECTED_NULL.getMessage(), null);
        }
        return ApiResponse.onSuccess(protectedInfo);
    }

    @Override
    @GetMapping("/phone_num")
    public ApiResponse<ProtectedResponseDTO.protectedPhoneNumber> getPhoneNumber() {
        ProtectedResponseDTO.protectedPhoneNumber protectedPhoneNumber = protectedQueryService.getPhoneNumber(2L);
        return ApiResponse.onSuccess(protectedPhoneNumber);
    }
}
