package hansung.ElderCare.Server.service.protectedService;

import hansung.ElderCare.Server.apiPayload.code.status.ErrorStatus;
import hansung.ElderCare.Server.apiPayload.exception.ProtectedHandler;
import hansung.ElderCare.Server.apiPayload.exception.UA_UD_UPHandler;
import hansung.ElderCare.Server.apiPayload.exception.UserHandler;
import hansung.ElderCare.Server.converter.AddressConverter;
import hansung.ElderCare.Server.domain.Protected;
import hansung.ElderCare.Server.domain.UA_UD_UP;
import hansung.ElderCare.Server.domain.User;
import hansung.ElderCare.Server.domain.enums.Relationship;
import hansung.ElderCare.Server.dto.ProtectedDTO.ProtectedRequestDTO;
import hansung.ElderCare.Server.dto.ProtectedDTO.ProtectedResponseDTO;
import hansung.ElderCare.Server.repository.ProtectedRepository;
import hansung.ElderCare.Server.repository.UA_UD_UPRepository;
import hansung.ElderCare.Server.repository.UserRepository;
import hansung.ElderCare.Server.service.userService.UserCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class ProtectedCommandServiceImpl implements ProtectedCommandService{

    private final ProtectedRepository protectedRepository;
    private final UserRepository userRepository;
    private final UA_UD_UPRepository uaUdUpRepository;

    @Override
// 보호대상자 등록
    public Long registrationProtected(ProtectedRequestDTO.RegistrationDTO registrationDTO, Long userId) {

        // 이미 등록된 보호대상자가 있다면 예외처리
        // 전화번호로 중복확인
        Optional<UA_UD_UP> uaUdUpOptionalWithProtected = uaUdUpRepository.findByUserIdWithProtected(userId);

        if (uaUdUpOptionalWithProtected.isPresent()) {
            throw new ProtectedHandler(ErrorStatus.PROTECTED_ALREADY_EXISTS);
        }

        // 보호대상자 정보 등록
        Protected protect = Protected.builder()
                .name(registrationDTO.getName())
                .birthDate(registrationDTO.getBirthDate())
                .nickname(registrationDTO.getNickname())
                .phoneNumber(registrationDTO.getPhoneNumber())
                .address(AddressConverter.toAddressEntity(registrationDTO.getAddress()))
                .protectedImageUrl(registrationDTO.getProtectedImageUrl())
                .build();
        protectedRepository.save(protect);

        // 보호대상자 테이블과 사용자-보호대상자 관계 테이블 매핑
        // 일단 사용자의 기본키는 1L
        Optional<UA_UD_UP> uaUdUpOptionalWithUser = uaUdUpRepository.findByUserIdWithUser(userId);
        if (uaUdUpOptionalWithUser.isEmpty()) {
            throw new UserHandler(ErrorStatus.USER_NOT_IN_RELATIONAL);
        }

        UA_UD_UP uaUdUp = uaUdUpOptionalWithUser.get();
        uaUdUp.setProtected(protect);
        return protect.getId();
    }
}
