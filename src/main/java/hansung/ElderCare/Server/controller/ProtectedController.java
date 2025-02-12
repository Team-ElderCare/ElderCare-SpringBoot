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
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Protected", description = "보호대상자 관련 API")
@RestController
@RequestMapping("/protected")
@RequiredArgsConstructor
@Slf4j
public class ProtectedController implements ProtectedSpecification {

    private final ProtectedCommandServiceImpl protectedCommandService;
    private final ProtectedQueryService protectedQueryService;

    @Override
    @PostMapping("/registration")
    public ApiResponse<?> registerProtected(@RequestBody @Valid ProtectedRequestDTO.RegistrationDTO request, Errors errors, BindingResult bindingResult) {

        // 유효성 검사 실패 시 클라이언트에게 어떤 필드에 대해 유효성 검사가 실패했는지에 대한 정보를 담은 Map 객체 반환
        if (errors.hasErrors()) {
            Map<String, String> validateResult = protectedCommandService.validateHandling(bindingResult);

            return ApiResponse.onFailure(ErrorStatus.PROTECTED_DATA_UNSATISFIED.getCode(),
                    ErrorStatus.PROTECTED_DATA_UNSATISFIED.getMessage(), validateResult);
        }

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

    @Override
    @PostMapping("/health")
    public ApiResponse<?> registerHealthInfo(@RequestBody @Valid ProtectedRequestDTO.ProtectedHealthInfo request, BindingResult bindingResult) {
        // 유효성 검사 실패 시 클라이언트에게 어떤 필드에 대해 유효성 검사가 실패했는지에 대한 정보를 담은 Map 객체 반환
        if (bindingResult.hasErrors()) {
            log.info("유효성 검사 실패");
            Map<String, String> validateResult = protectedCommandService.validateHandling(bindingResult);

            return ApiResponse.onFailure(ErrorStatus.PROTECTED_DATA_UNSATISFIED.getCode(),
                    ErrorStatus.PROTECTED_DATA_UNSATISFIED.getMessage(), validateResult);
        }

        // 전달받은 건강 정보 저장
        protectedCommandService.registerHealth(request, 1L);
        return ApiResponse.onSuccess();
    }

}
