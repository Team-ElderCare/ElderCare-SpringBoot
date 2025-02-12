package hansung.ElderCare.Server.service.protectedService;

import hansung.ElderCare.Server.apiPayload.code.status.ErrorStatus;
import hansung.ElderCare.Server.apiPayload.exception.ProtectedHandler;
import hansung.ElderCare.Server.apiPayload.exception.UA_UD_UPHandler;
import hansung.ElderCare.Server.apiPayload.exception.UserHandler;
import hansung.ElderCare.Server.converter.AddressConverter;
import hansung.ElderCare.Server.domain.*;
import hansung.ElderCare.Server.domain.enums.BloodType;
import hansung.ElderCare.Server.domain.enums.Relationship;
import hansung.ElderCare.Server.dto.ProtectedDTO.ProtectedRequestDTO;
import hansung.ElderCare.Server.dto.ProtectedDTO.ProtectedResponseDTO;
import hansung.ElderCare.Server.repository.*;
import hansung.ElderCare.Server.service.userService.UserCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class ProtectedCommandServiceImpl implements ProtectedCommandService{

    private final ProtectedRepository protectedRepository;
    private final UA_UD_UPRepository uaUdUpRepository;
    private final AllergyRepository allergyRepository;
    private final Protected_AllergyRepository protectedAllergyRepository;
    private final VaccineRepository vaccineRepository;
    private final Protected_VaccineRepository protectedVaccineRepository;
    private final SurgeryRepository surgeryRepository;
    private final Protected_SurgeryRepository protectedSurgeryRepository;

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

    @Override
    // Validation 검사 실패한 속성들을 담고 있는 Map 반환하는 메소드
    public Map<String, String> validateHandling(BindingResult bindingResult) {
        Map<String, String> validatorResult = new HashMap<>();

        for (FieldError error : bindingResult.getFieldErrors()) {
            String validKeyName = String.format("%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }

        return validatorResult;
    }

    @Override
    // 보호대상자 건강정보 등록
    public Boolean registerHealth(ProtectedRequestDTO.ProtectedHealthInfo request, Long userId) {

        // 현재 사용자에 등록되어 있는 보호대상자 있는지 확인
        Optional<UA_UD_UP> uaUdUpOptional = uaUdUpRepository.findByUserIdWithProtected(userId);
        if (uaUdUpOptional.isEmpty()) {
            throw new ProtectedHandler(ErrorStatus.PROTECTED_NULL);
        }

        // 우선 보호대상자 키, 몸무게, 혈액형 저장
        Protected aProtected = uaUdUpOptional.get().getProtected();
        aProtected.setHeight(request.getHeight());
        aProtected.setWeight(request.getWeight());
        // BloodType fromString 메소드 안에 이미 예외처리 구문 있음
        aProtected.setBloodType(BloodType.fromString(request.getBloodType()));

        protectedRepository.save(aProtected);

        // 요청받은 알레르기 리스트 중 알레르기 테이블에 이미 저장되어 있는 instance가 있는지 확인
        List<String> allergiesList = request.getAllergies();

        for (String allergyName : allergiesList) {
            Optional<Allergy> allergyOptional = allergyRepository.findByAllergyName(allergyName);

            // 이미 저장되어 있는 데이터가 있다면 기존 instance와 관계 테이블 매핑
            if (allergyOptional.isPresent()) {
                Protected_Allergy protectedAllergy = Protected_Allergy.builder()
                        .Protected(aProtected)
                        .allergy(allergyOptional.get())
                        .build();
                protectedAllergyRepository.save(protectedAllergy);
            }
            // 그렇지 않고 기존에 저장되어 있는 데이터가 없다면 새로 테이블에 저장 후 관계 테이블 매핑
            else {
                Allergy allergy = Allergy.builder()
                        .allergyName(allergyName)
                        .build();
                allergyRepository.save(allergy);

                Protected_Allergy protectedAllergy = Protected_Allergy.builder()
                        .allergy(allergy)
                        .Protected(aProtected)
                        .build();
                protectedAllergyRepository.save(protectedAllergy);
            }
        }

        // 요청받은 백신 리스트 중 백신 테이블에 이미 저장되어 있는 instance가 있는지 확인
        List<String> vaccinesList = request.getVaccines();

        for (String vaccineName : vaccinesList) {
            Optional<Vaccine> vaccineOptional = vaccineRepository.findByName(vaccineName);

            // 이미 저장되어 있는 백신 데이터가 있다면 기존 인스턴스와 관계 테이블 매핑
            if (vaccineOptional.isPresent()) {
                Protected_Vaccine protectedVaccine = Protected_Vaccine.builder()
                        .Protected(aProtected)
                        .vaccine(vaccineOptional.get())
                        .build();
                protectedVaccineRepository.save(protectedVaccine);
            }
            // 해당 백신 이름이 저장되어 있지 않다면 백신 테이블에 백신 데이터 저장 후 기존 인스턴스와 관계 테이블 매핑
            else {
                Vaccine vaccine = Vaccine.builder()
                        .name(vaccineName)
                        .build();
                vaccineRepository.save(vaccine);

                Protected_Vaccine protectedVaccine = Protected_Vaccine.builder()
                        .Protected(aProtected)
                        .vaccine(vaccine)
                        .build();
                protectedVaccineRepository.save(protectedVaccine);
            }
        }

        // 요청받은 수술 리스트 중 수술 테이블에 이미 저장되어 있는 instance가 있는지 확인
        List<String> surgeriesList = request.getSurgeries();
        for (String surgeryName : surgeriesList) {
            Optional<Surgery> surgeryOptional = surgeryRepository.findByName(surgeryName);
            // 이미 저장되어 있는 수술 데이터가 있다면 기존 인스턴스와 관계 테이블 매핑
            if (surgeryOptional.isPresent()) {
                Protected_Surgery protectedSurgery = Protected_Surgery.builder()
                        .Protected(aProtected)
                        .surgery(surgeryOptional.get())
                        .build();

                protectedSurgeryRepository.save(protectedSurgery);
            }
            // 해당 수술 이름이 저장되어 있지 않다면 수술 테이블에 수술 데이터 저장 후 기존 인스턴스와 관계 테이블 매핑
            else {
                Surgery surgery = Surgery.builder()
                        .name(surgeryName)
                        .build();
                surgeryRepository.save(surgery);

                Protected_Surgery protectedSurgery = Protected_Surgery.builder()
                        .surgery(surgery)
                        .Protected(aProtected)
                        .build();
                protectedSurgeryRepository.save(protectedSurgery);
            }
        }

        return true;
    }
}
