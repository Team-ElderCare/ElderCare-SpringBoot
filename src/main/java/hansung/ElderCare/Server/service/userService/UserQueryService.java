package hansung.ElderCare.Server.service.userService;

import hansung.ElderCare.Server.dto.UserDTO.UserResponseDTO;
import org.springframework.transaction.annotation.Transactional;

public interface UserQueryService {

    @Transactional
    UserResponseDTO.UserDTO getUserInfo(Long userId);
}
