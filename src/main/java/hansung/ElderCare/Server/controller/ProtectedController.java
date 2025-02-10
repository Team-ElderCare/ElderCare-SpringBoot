package hansung.ElderCare.Server.controller;

import hansung.ElderCare.Server.apiPayload.ApiResponse;
import hansung.ElderCare.Server.controller.specification.ProtectedSpecification;
import hansung.ElderCare.Server.dto.ProtectedDTO.ProtectedRequestDTO;
import hansung.ElderCare.Server.dto.ProtectedDTO.ProtectedResponseDTO;
import hansung.ElderCare.Server.dto.UserDTO.UserRequestDTO;
import hansung.ElderCare.Server.service.protectedService.ProtectedCommandService;
import hansung.ElderCare.Server.service.protectedService.ProtectedCommandServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Protected", description = "보호대상자 관련 API")
@RestController
@RequestMapping("/protected")
@RequiredArgsConstructor
public class ProtectedController implements ProtectedSpecification {

    private final ProtectedCommandServiceImpl protectedCommandService;

    @Override
    @PostMapping("/registration")
    public ApiResponse<Long> registerProtected(@RequestBody @Valid ProtectedRequestDTO.RegistrationDTO registrationDTO) {
        Long id = protectedCommandService.registrationProtected(registrationDTO);
        return ApiResponse.onSuccess(id);
    }
}
