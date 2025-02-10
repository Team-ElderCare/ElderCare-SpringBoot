package hansung.ElderCare.Server.converter;

import hansung.ElderCare.Server.domain.Protected;
import hansung.ElderCare.Server.dto.ProtectedDTO.ProtectedResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ProtectedConverter {
    public static ProtectedResponseDTO.ProtectedInfo toProtectedInfo(Protected aProtected) {
        ProtectedResponseDTO.ProtectedInfo protectedInfo = ProtectedResponseDTO.ProtectedInfo.builder()
                .name(aProtected.getName())
                .nickname(aProtected.getNickname())
                .address(AddressConverter.toAddressDTO(aProtected.getAddress()))
                .birthDate(aProtected.getBirthDate())
                .phoneNumber(aProtected.getPhoneNumber())
                .protectedImageUrl(aProtected.getProtectedImageUrl())
                .build();

        return protectedInfo;

    }
}
