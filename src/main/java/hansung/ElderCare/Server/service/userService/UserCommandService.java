package hansung.ElderCare.Server.service.userService;

import hansung.ElderCare.Server.dto.UserDTO.UserRequestDTO;
import hansung.ElderCare.Server.dto.UserDTO.UserResponseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface UserCommandService {

    public UserResponseDTO.UserDTO updateUserInfo(Long userId, UserRequestDTO.UserInfoEditDTO request, MultipartFile profileImage);
}
