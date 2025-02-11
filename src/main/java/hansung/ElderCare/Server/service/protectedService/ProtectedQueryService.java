package hansung.ElderCare.Server.service.protectedService;

import hansung.ElderCare.Server.dto.ProtectedDTO.ProtectedResponseDTO;

public interface ProtectedQueryService {
    ProtectedResponseDTO.ProtectedInfo getProtectedInfo(Long id);

    ProtectedResponseDTO.protectedPhoneNumber getPhoneNumber(Long id);
}
