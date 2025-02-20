package hansung.ElderCare.Server.service.userService;

import hansung.ElderCare.Server.domain.User;
import hansung.ElderCare.Server.dto.UserDTO.UserRequestDTO;
import hansung.ElderCare.Server.dto.UserDTO.UserResponseDTO;

public interface UserCommandService {

    public UserResponseDTO.UserDTO updateUserInfo(Long userId, UserRequestDTO.UserInfoEditDTO request);
}
