package hansung.ElderCare.Server.service.protectedService;

import hansung.ElderCare.Server.dto.ProtectedDTO.ProtectedRequestDTO;

public interface ProtectedCommandService {
    Long registrationProtected(ProtectedRequestDTO.RegistrationDTO registrationDTO);
}
