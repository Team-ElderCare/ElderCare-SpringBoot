package hansung.ElderCare.Server.converter;

import hansung.ElderCare.Server.domain.Protected;
import hansung.ElderCare.Server.domain.enums.BloodType;
import hansung.ElderCare.Server.dto.ProtectedDTO.ProtectedResponseDTO;
import hansung.ElderCare.Server.repository.Protected_AllergyRepository;
import hansung.ElderCare.Server.repository.Protected_SurgeryRepository;
import hansung.ElderCare.Server.repository.Protected_VaccineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProtectedConverter {

    private final Protected_AllergyRepository protectedAllergyRepository;
    private final Protected_VaccineRepository protectedVaccineRepository;
    private final Protected_SurgeryRepository protectedSurgeryRepository;

    public static ProtectedResponseDTO.ProtectedInfo toProtectedInfo(Protected aProtected) {
        return ProtectedResponseDTO.ProtectedInfo.builder()
                .name(aProtected.getName())
                .nickname(aProtected.getNickname())
                .address(AddressConverter.toResponseAddressDTO(aProtected.getAddress()))
                .birthDate(aProtected.getBirthDate())
                .phoneNumber(aProtected.getPhoneNumber())
                .build();

    }

    public static ProtectedResponseDTO.protectedPhoneNumber toProtectedPhoneNumber(Protected aProtected) {
        return ProtectedResponseDTO.protectedPhoneNumber.builder()
                .phoneNumber(aProtected.getPhoneNumber())
                .build();
    }

    public ProtectedResponseDTO.protectedHealthInfo toProtectedHealthInfo(Protected aProtected) {
        return ProtectedResponseDTO.protectedHealthInfo.builder()
                .height(aProtected.getHeight())
                .weight(aProtected.getWeight())
                .bloodType(aProtected.getBloodType().getDisplayName())
                .allergies(protectedAllergyRepository.findAllergyNamesByProtectedId(aProtected.getId()))
                .vaccines(protectedVaccineRepository.findVaccineNamesByProtectedId(aProtected.getId()))
                .surgeries(protectedSurgeryRepository.findSurgeryNamesByProtectedId(aProtected.getId()))
                .build();
    }
}
