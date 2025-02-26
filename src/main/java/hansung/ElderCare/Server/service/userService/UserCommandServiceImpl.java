package hansung.ElderCare.Server.service.userService;

import hansung.ElderCare.Server.apiPayload.code.status.ErrorStatus;
import hansung.ElderCare.Server.apiPayload.exception.ImageHandler;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponseDTO.UserDTO updateUserInfo(Long userId , UserRequestDTO.UserInfoEditDTO request, MultipartFile profileImage){



        //사용자 검색
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND)); //사용자 없으면 오류

        //Patch이기 때문에 기존값과 다를 경우에만 업데이트
        if(!request.getName().equals(user.getUserName())){
            user.setUserName(request.getName());
        }

        if(!request.getPhoneNumber().equals(user.getUserName())){
            user.setPhoneNumber(request.getPhoneNumber());
        }

        if(request.getRelationship() != null){
            try {
                Relationship newRelationship = Relationship.valueOf(request.getRelationship());

                if(user.getRelationship() != newRelationship) {
                    user.setRelationship(newRelationship);
                }
            } catch (IllegalArgumentException e) {
                throw new UserHandler(ErrorStatus._BAD_REQUEST);
            }
        }

        // 이미지 처리
        if (profileImage != null && !profileImage.isEmpty()) {
            try {
                // 이미지 유효성 검사
                validateImage(profileImage);

                // 이미지 업로드 및 URL 저장
                String fileName = profileImage.getOriginalFilename();
                log.info("이미지 업로드 처리: 파일명 = {}, 크기 = {} bytes", fileName, profileImage.getSize());
                user.setUserImageUrl("http://example.com/profile-images/" + fileName);

            } catch (IOException e) {
                log.error("이미지 처리 중 오류 발생", e);
                throw new UserHandler(ErrorStatus._INTERNAL_SERVER_ERROR);
            }
        }


        //후에 s3구축후 프로필 이미지 업로드후 새로운 url 받아오는 기능
//        String imageUrl = null;
//        if (proofRequest.getCertificationImage() != null && !proofRequest.getCertificationImage().isEmpty()) {
//            imageUrl = imageService.upload(proofRequest.getCertificationImage());
//        }


        userRepository.save(user);

        return UserConverter.toUserInfoDTO(user); //변경후 사용자 정보 리턴
    }

    private void validateImage(MultipartFile file) throws IOException {
        // 파일 타입 확인
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new UserHandler(ErrorStatus._BAD_REQUEST);
        }

        // 파일 크기 확인 (최대 5MB)
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new ImageHandler(ErrorStatus.IMAGE_SIZE_OVER);
        }

        // 추가적인 이미지 유효성 검사 로직 구현 가능
    }

}
