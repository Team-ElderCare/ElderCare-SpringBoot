package hansung.ElderCare.Server.service.userService;

import hansung.ElderCare.Server.apiPayload.code.status.ErrorStatus;
import hansung.ElderCare.Server.apiPayload.exception.UserHandler;
import hansung.ElderCare.Server.converter.UserConverter;
import hansung.ElderCare.Server.domain.User;
import hansung.ElderCare.Server.dto.UserDTO.UserResponseDTO;
import hansung.ElderCare.Server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;

    @Override
    public UserResponseDTO.UserDTO getUserInfo(Long userId){

        User user = userRepository.findById(userId)
                .orElse(null);

        if(user == null){
            throw new UserHandler(ErrorStatus.USER_NOT_FOUND);
        }


        return UserConverter.toUserInfoDTO(user);
    };
}
