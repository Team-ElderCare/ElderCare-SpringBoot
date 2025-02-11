package hansung.ElderCare.Server.converter;

import hansung.ElderCare.Server.domain.Address;
import hansung.ElderCare.Server.dto.ProtectedDTO.ProtectedRequestDTO;
import hansung.ElderCare.Server.dto.ProtectedDTO.ProtectedResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class AddressConverter {
    public static Address toAddressEntity(ProtectedResponseDTO.ProtectedInfo.AddressDTO addressDTO) {

        return Address.builder()
                .zipCode(addressDTO.getZipcode())
                .building(addressDTO.getBuilding())
                .detailedAddress(addressDTO.getDetailedAddress())
                .build();
    }

    // Entity -> ProtectedInfo 안에 Address DTO 변환
    public static ProtectedResponseDTO.ProtectedInfo.AddressDTO toResponseAddressDTO(Address address) {
        return ProtectedResponseDTO.ProtectedInfo.AddressDTO.builder()
                .zipcode(address.getZipCode())
                .building(address.getBuilding())
                .detailedAddress(address.getDetailedAddress())
                .build();
    }

    // Entity -> RegistrationDTO 안에 AddressDTO 변환
    public static ProtectedRequestDTO.RegistrationDTO.AddressDTO toRequestAddressDTO(Address address) {
        return ProtectedRequestDTO.RegistrationDTO.AddressDTO.builder()
                .zipcode(address.getZipCode())
                .building(address.getBuilding())
                .detailedAddress(address.getDetailedAddress())
                .build();
    }
}
