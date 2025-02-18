package hansung.ElderCare.Server.service.protectedService;

import hansung.ElderCare.Server.apiPayload.code.status.ErrorStatus;
import hansung.ElderCare.Server.apiPayload.exception.ProtectedHandler;
import hansung.ElderCare.Server.apiPayload.exception.UA_UD_UPHandler;
import hansung.ElderCare.Server.apiPayload.exception.UserHandler;
import hansung.ElderCare.Server.converter.AddressConverter;
import hansung.ElderCare.Server.converter.ProtectedConverter;
import hansung.ElderCare.Server.domain.*;
import hansung.ElderCare.Server.domain.enums.BloodType;
import hansung.ElderCare.Server.domain.enums.Relationship;
import hansung.ElderCare.Server.dto.ProtectedDTO.ProtectedRequestDTO;
import hansung.ElderCare.Server.dto.ProtectedDTO.ProtectedResponseDTO;
import hansung.ElderCare.Server.repository.*;
import hansung.ElderCare.Server.service.userService.UserCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.*;

@Transactional
@RequiredArgsConstructor
@Service
@Slf4j
public class ProtectedCommandServiceImpl implements ProtectedCommandService{

    private final ProtectedRepository protectedRepository;
    private final UA_UD_UPRepository uaUdUpRepository;
    private final AllergyRepository allergyRepository;
    private final Protected_AllergyRepository protectedAllergyRepository;
    private final VaccineRepository vaccineRepository;
    private final Protected_VaccineRepository protectedVaccineRepository;
    private final SurgeryRepository surgeryRepository;
    private final Protected_SurgeryRepository protectedSurgeryRepository;

    private final ProtectedConverter protectedConverter;

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
    public ProtectedResponseDTO.protectedHealthInfo registerHealth(ProtectedRequestDTO.ProtectedHealthInfo request, Long userId) {

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

        // 알레르기 리스트 저장
        List<String> allergiesList = request.getAllergies();
        for (String allergyName : allergiesList) {
            Allergy allergy = Allergy.builder()
                    .allergyName(allergyName)
                    .build();

            allergyRepository.save(allergy);

            Protected_Allergy protectedAllergy = Protected_Allergy.builder()
                    .Protected(aProtected)
                    .allergy(allergy)
                    .build();
            protectedAllergyRepository.save(protectedAllergy);
        }

        // 백신 리스트 저장
        List<String> vaccinesList = request.getVaccines();
        for (String vaccineName : vaccinesList) {
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

        // 수술 리스트 저장
        List<String> surgeriesList = request.getSurgeries();
        for (String surgeryName : surgeriesList) {
            Surgery surgery = Surgery.builder()
                    .name(surgeryName)
                    .build();
            surgeryRepository.save(surgery);

            Protected_Surgery protectedSurgery = Protected_Surgery.builder()
                    .Protected(aProtected)
                    .surgery(surgery)
                    .build();
            protectedSurgeryRepository.save(protectedSurgery);
        }

        ProtectedResponseDTO.protectedHealthInfo response = protectedConverter.toProtectedHealthInfo(aProtected);
        return response;
    }

    @Override
    // 키 몸무게 수정 후 -> 보호대상자 전체 건강정보 Controller에 반환
    public ProtectedResponseDTO.protectedHealthInfo updateHeightWeight(ProtectedRequestDTO.HeightWeightDTO request, Long userId) {
        Protected aProtected = uaUdUpRepository.findByUserIdWithProtected(userId)
                .orElseThrow(() -> new ProtectedHandler(ErrorStatus.PROTECTED_NULL))
                .getProtected();

        // 새로운 키, 몸무게 저장
        aProtected.setHeight(request.getHeight());
        aProtected.setWeight(request.getWeight());
        protectedRepository.save(aProtected);

        ProtectedResponseDTO.protectedHealthInfo response = protectedConverter.toProtectedHealthInfo(aProtected);

        return response;
    }

    @Override
    public ProtectedResponseDTO.protectedHealthInfo updateBloodType(ProtectedRequestDTO.BloodTypeDTO request, Long userId) {
        Protected aProtected = uaUdUpRepository.findByUserIdWithProtected(userId)
                .orElseThrow(() -> new ProtectedHandler(ErrorStatus.PROTECTED_NULL))
                .getProtected();

        // 혈액형 저장
        aProtected.setBloodType(BloodType.fromString(request.getBloodType()));
        protectedRepository.save(aProtected);

        ProtectedResponseDTO.protectedHealthInfo response = protectedConverter.toProtectedHealthInfo(aProtected);

        return response;
    }

    @Override
    public ProtectedResponseDTO.protectedHealthInfo updateAllergy(ProtectedRequestDTO.AllergiesDTO request, Long userId) {
        Protected aProtected = uaUdUpRepository.findByUserIdWithProtected(userId)
                .orElseThrow(() -> new ProtectedHandler(ErrorStatus.PROTECTED_NULL))
                .getProtected();

        // 기존에 있던 알레르기 레코드와 연결 끊기
        protectedAllergyRepository.deleteByProtectedId(aProtected.getId());

        List<String> allergyNames = request.getAllergies();

        // 기존 알러지가 있으면 재사용, 없으면 새로 생성
        for (String allergyName : allergyNames) {
            Allergy allergy = allergyRepository.findByAllergyName(allergyName)
                    .orElseGet(() -> {
                        Allergy newAllergy = Allergy.builder()
                                .allergyName(allergyName)
                                .build();
                        return allergyRepository.save(newAllergy);
                    });

            Protected_Allergy protectedAllergy = Protected_Allergy.builder()
                    .allergy(allergy)
                    .Protected(aProtected)
                    .build();
            protectedAllergyRepository.save(protectedAllergy);
        }
        // 건강정보 바로 리턴
        return protectedConverter.toProtectedHealthInfo(aProtected);
    }

    @Override
    public ProtectedResponseDTO.protectedHealthInfo updateVaccine(ProtectedRequestDTO.VaccinesDTO request, Long userId) {
        Protected aProtected = uaUdUpRepository.findByUserIdWithProtected(userId)
                .orElseThrow(() -> new ProtectedHandler(ErrorStatus.PROTECTED_NULL))
                .getProtected();

        // 보호대상자와 백신관의 관계 끊기
        protectedVaccineRepository.deleteByProtectedId(aProtected.getId());

        List<String> vaccineNames = request.getVaccines();

        for (String vaccineName : vaccineNames) {
            Vaccine vaccine = vaccineRepository.findByName(vaccineName)
                    .orElseGet(() -> {
                        Vaccine newVaccine = Vaccine.builder()
                                .name(vaccineName)
                                .build();
                        return vaccineRepository.save(newVaccine);
                    });
            Protected_Vaccine protectedVaccine = Protected_Vaccine.builder()
                    .Protected(aProtected)
                    .vaccine(vaccine)
                    .build();
            protectedVaccineRepository.save(protectedVaccine);
        }
        return protectedConverter.toProtectedHealthInfo(aProtected);
    }

    public ProtectedResponseDTO.protectedHealthInfo updateSurgery(ProtectedRequestDTO.SurgeriesDTO request, Long userId) {
        Protected aProtected = uaUdUpRepository.findByUserIdWithProtected(userId)
                .orElseThrow(() -> new ProtectedHandler(ErrorStatus.PROTECTED_NULL))
                .getProtected();
        protectedSurgeryRepository.deleteByProtectedId(aProtected.getId());

        List<String> surgeryNames = request.getSurgeries();
        for (String surgeryName : surgeryNames) {
            Surgery surgery = surgeryRepository.findByName(surgeryName)
                    .orElseGet(() -> {
                        Surgery newSurgery = Surgery.builder()
                                .name(surgeryName)
                                .build();
                        return surgeryRepository.save(newSurgery);
                    });
            Protected_Surgery protectedSurgery = Protected_Surgery.builder()
                    .Protected(aProtected)
                    .surgery(surgery)
                    .build();
            protectedSurgeryRepository.save(protectedSurgery);
        }
        return protectedConverter.toProtectedHealthInfo(aProtected);
    }
}
