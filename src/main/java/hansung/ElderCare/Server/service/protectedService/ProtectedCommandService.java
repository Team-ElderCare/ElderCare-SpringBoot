package hansung.ElderCare.Server.service.protectedService;

import hansung.ElderCare.Server.dto.ProtectedDTO.ProtectedRequestDTO;
import hansung.ElderCare.Server.dto.ProtectedDTO.ProtectedResponseDTO;

public interface ProtectedCommandService {
    Long registrationProtected(ProtectedRequestDTO.RegistrationDTO registrationDTO, Long userId);
}
