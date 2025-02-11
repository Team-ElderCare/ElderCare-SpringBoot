package hansung.ElderCare.Server.converter;

import hansung.ElderCare.Server.domain.Address;
import hansung.ElderCare.Server.dto.ProtectedDTO.ProtectedRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class AddressConverter {
    // DTO -> Entity 변환
    public static Address toAddressEntity(ProtectedRequestDTO.RegistrationDTO.AddressDTO addressDTO) {

        return Address.builder()
                .zipCode(addressDTO.getZipcode())
                .building(addressDTO.getBuilding())
                .detailedAddress(addressDTO.getDetailedAddress())
                .build();
    }

    // Entity -> DTO 변환
    public static ProtectedRequestDTO.RegistrationDTO.AddressDTO toAddressDTO(Address address) {
        return ProtectedRequestDTO.RegistrationDTO.AddressDTO.builder()
                .zipcode(address.getZipCode())
                .building(address.getBuilding())
                .detailedAddress(address.getDetailedAddress())
                .build();
    }
}
