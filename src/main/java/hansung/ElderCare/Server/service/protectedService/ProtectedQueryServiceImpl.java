package hansung.ElderCare.Server.service.protectedService;

import hansung.ElderCare.Server.apiPayload.code.status.ErrorStatus;
import hansung.ElderCare.Server.apiPayload.exception.ProtectedHandler;
import hansung.ElderCare.Server.apiPayload.exception.UA_UD_UPHandler;
import hansung.ElderCare.Server.converter.ProtectedConverter;
import hansung.ElderCare.Server.domain.Protected;
import hansung.ElderCare.Server.domain.UA_UD_UP;
import hansung.ElderCare.Server.dto.ProtectedDTO.ProtectedResponseDTO;
import hansung.ElderCare.Server.repository.UA_UD_UPRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProtectedQueryServiceImpl implements ProtectedQueryService{

    private final UA_UD_UPRepository uaUdUpRepository;
    @Override
    // User의 id를 통해 보호대상자 정보 조회
    // User는 한 명만 있으며 id는 1이라고 가정, 관계테이블에도 해당 유저에 대한 외래키 1을 저장했다고 가정
    public ProtectedResponseDTO.ProtectedInfo getProtectedInfo(Long userId) {

        Optional<UA_UD_UP> uaUdUpOptional = uaUdUpRepository.findByUserIdWithProtected(userId);
        if(uaUdUpOptional.isEmpty()) {
            throw new UA_UD_UPHandler(ErrorStatus.USER_NOT_IN_RELATIONAL);
        }

        UA_UD_UP uaUdUp = uaUdUpOptional.get();

        // 보호대상자 get
        Protected aProtected = uaUdUp.getProtected();
        if (aProtected == null) {
            return null;
        }
        // DTO 변환 후 Controller에 반환

        ProtectedResponseDTO.ProtectedInfo protectedInfo = ProtectedConverter.toProtectedInfo(aProtected);

        return protectedInfo;
    }

    @Override
    // 사용자 기본키를 이용해 보호대상자 전화번호 get
    public ProtectedResponseDTO.protectedPhoneNumber getPhoneNumber(Long userId) {
        Optional<UA_UD_UP> uaUdUpOptional = uaUdUpRepository.findByUserIdWithProtected(userId);
        if (uaUdUpOptional.isEmpty()) {
            throw new UA_UD_UPHandler(ErrorStatus.USER_NOT_IN_RELATIONAL);
        }

        UA_UD_UP uaUdUp = uaUdUpOptional.get();
        Protected aProtected = uaUdUp.getProtected();
        // 보호대상자가 아직 등록되지 않았을 때
        if (aProtected == null) {
            throw new ProtectedHandler(ErrorStatus.PROTECTED_NULL);
        }

        // 보호대상자의 전화번호가 등록되지 않았을 때
        if (aProtected.getPhoneNumber() == null) {
            throw new ProtectedHandler(ErrorStatus.PROTECTED_NO_PHONE_NUMBER);
        }

        ProtectedResponseDTO.protectedPhoneNumber protectedPhoneNumber = ProtectedConverter.toProtectedPhoneNumber(aProtected);
        return protectedPhoneNumber;
    }
}
