package hansung.ElderCare.Server.service.protectedService;

import hansung.ElderCare.Server.dto.ProtectedDTO.ProtectedRequestDTO;
import hansung.ElderCare.Server.dto.ProtectedDTO.ProtectedResponseDTO;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import java.util.Map;

public interface ProtectedCommandService {
    Long registrationProtected(ProtectedRequestDTO.RegistrationDTO registrationDTO, Long userId);

    Map<String, String> validateHandling(BindingResult bindingResult);

    Boolean registerHealth(ProtectedRequestDTO.ProtectedHealthInfo request, Long userId);
}
