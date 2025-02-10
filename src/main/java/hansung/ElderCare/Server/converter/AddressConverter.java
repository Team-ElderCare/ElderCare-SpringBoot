package hansung.ElderCare.Server.converter;

import hansung.ElderCare.Server.domain.Address;
import hansung.ElderCare.Server.dto.ProtectedDTO.ProtectedRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class AddressConverter {
    public static Address toAddressEntity(ProtectedRequestDTO.RegistrationDTO.AddressDTO addressDTO) {

        return Address.builder()
                .zipCode(addressDTO.getZipcode())
                .building(addressDTO.getBuilding())
                .detailedAddress(addressDTO.getDetailedAddress())
                .build();
    }
}
