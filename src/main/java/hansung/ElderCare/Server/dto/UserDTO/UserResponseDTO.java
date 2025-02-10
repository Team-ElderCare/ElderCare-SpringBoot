package hansung.ElderCare.Server.dto.UserDTO;

import hansung.ElderCare.Server.domain.enums.Relationship;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserResponseDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserDTO {

        @Schema(description = "보호자(사용자) 이름")
        private String name;

        @Schema(description = "프로필사진 url")
        private String imageUrl;

        @Schema(description = "전화번호")
        private String phoneNumber;

        @Schema(description = "보호대상자와의 관계")
        private Relationship relationship;
    }

}
