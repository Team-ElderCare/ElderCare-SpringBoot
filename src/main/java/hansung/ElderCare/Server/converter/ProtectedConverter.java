package hansung.ElderCare.Server.converter;

import hansung.ElderCare.Server.domain.Protected;
import hansung.ElderCare.Server.dto.ProtectedDTO.ProtectedResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ProtectedConverter {
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
}
