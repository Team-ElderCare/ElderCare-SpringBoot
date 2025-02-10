package hansung.ElderCare.Server.converter;

import hansung.ElderCare.Server.domain.User;
import hansung.ElderCare.Server.dto.UserDTO.UserResponseDTO;

public class UserConverter {

    public static UserResponseDTO.UserDTO toUserInfoDTO(User user) {

        return UserResponseDTO.UserDTO.builder()
                .name(user.getUserName())
                .imageUrl(user.getUserImageUrl())
                .phoneNumber(user.getPhoneNumber())
                .relationship(user.getRelationship())
                .build();


    }
}
