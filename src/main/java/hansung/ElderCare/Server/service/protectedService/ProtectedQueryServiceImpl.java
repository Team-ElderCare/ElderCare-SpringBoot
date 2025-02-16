package hansung.ElderCare.Server.service.protectedService;

import hansung.ElderCare.Server.apiPayload.code.status.ErrorStatus;
import hansung.ElderCare.Server.apiPayload.exception.ProtectedHandler;
import hansung.ElderCare.Server.apiPayload.exception.UA_UD_UPHandler;
import hansung.ElderCare.Server.converter.ProtectedConverter;
import hansung.ElderCare.Server.domain.*;
import hansung.ElderCare.Server.domain.enums.BloodType;
import hansung.ElderCare.Server.dto.ProtectedDTO.ProtectedResponseDTO;
import hansung.ElderCare.Server.repository.Protected_AllergyRepository;
import hansung.ElderCare.Server.repository.Protected_SurgeryRepository;
import hansung.ElderCare.Server.repository.Protected_VaccineRepository;
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
    private final Protected_AllergyRepository protectedAllergyRepository;
    private final Protected_SurgeryRepository protectedSurgeryRepository;
    private final Protected_VaccineRepository protectedVaccineRepository;

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

    @Override
    // 보호대상자의 건강정보 get
    public ProtectedResponseDTO.protectedHealthInfo getProtectedHealthInfo(Long userId) {
        // 아직 보호대상자 등록되어 있지 않았다면 예외 처리
        UA_UD_UP uaUdUp = uaUdUpRepository.findByUserIdWithProtected(userId)
                .orElseThrow(() -> new ProtectedHandler(ErrorStatus.PROTECTED_NULL));

        Long protectedId = uaUdUp.getProtected().getId();

        // 보호대상자의 건강정보 조회
        Protected aProtected = uaUdUp.getProtected();

        // 건강정보 입력이 필수 입력조건은 아니라 생각해서 예외처리는 하지 않겠으나
        // 혈액형 enum 클래스를 문자열로 받아오는 경우 건강정보를 입력하지 않아 null일 경우 NullPointerException 발생
        // 이 부분만 예외처리해서 끊김이 없이 실행되도록 함
        String bloodType = Optional.ofNullable(aProtected.getBloodType())
                .map(BloodType::getDisplayName)
                .orElse(null);

        ProtectedResponseDTO.protectedHealthInfo protectedHealthInfo = ProtectedResponseDTO.protectedHealthInfo.builder()
                .height(aProtected.getHeight())
                .weight(aProtected.getWeight())
                .bloodType(bloodType)
                .allergies(protectedAllergyRepository.findAllergyNamesByProtectedId(protectedId))
                .vaccines(protectedVaccineRepository.findVaccineNamesByProtectedId(protectedId))
                .surgeries(protectedSurgeryRepository.findSurgeryNamesByProtectedId(protectedId))
                .build();

        return protectedHealthInfo;
    }
}
