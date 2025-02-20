package hansung.ElderCare.Server.service.userService;

import hansung.ElderCare.Server.apiPayload.code.status.ErrorStatus;
import hansung.ElderCare.Server.apiPayload.exception.UserHandler;
import hansung.ElderCare.Server.converter.UserConverter;
import hansung.ElderCare.Server.domain.User;
import hansung.ElderCare.Server.domain.enums.Relationship;
import hansung.ElderCare.Server.dto.UserDTO.UserRequestDTO;
import hansung.ElderCare.Server.dto.UserDTO.UserResponseDTO;
import hansung.ElderCare.Server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponseDTO.UserDTO updateUserInfo(Long userId , UserRequestDTO.UserInfoEditDTO request){

        //사용자 검색
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND)); //사용자 없으면 오류

        //후에 s3구축후 프로필 이미지 업로드후 새로운 url 받아오는 기능
//        String imageUrl = null;
//        if (proofRequest.getCertificationImage() != null && !proofRequest.getCertificationImage().isEmpty()) {
//            imageUrl = imageService.upload(proofRequest.getCertificationImage());
//        }
        System.out.println("유저아이디 : " + userId);
        System.out.println("리퀘스트 :" + request);
        System.out.println("유저 :" + user);
        System.out.println("이름 : " + request.getName());
        System.out.println("관계 : " + request.getRelationship());

        user.setUserName(request.getName());
//        user.setUserImageUrl(imageUrl); //s3 구축 후 사용
        user.setUserImageUrl("http/update - example"); //이미지 url 하드코딩
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRelationship(Relationship.valueOf(request.getRelationship()));

        userRepository.save(user);

        return UserConverter.toUserInfoDTO(user); //변경후 사용자 정보 리턴
    }

}
