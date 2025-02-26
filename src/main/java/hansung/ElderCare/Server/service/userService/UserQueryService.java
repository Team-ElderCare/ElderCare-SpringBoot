package hansung.ElderCare.Server.service.userService;

import hansung.ElderCare.Server.dto.UserDTO.UserResponseDTO;

public interface UserQueryService {


    UserResponseDTO.UserDTO getUserInfo(Long userId);
}
