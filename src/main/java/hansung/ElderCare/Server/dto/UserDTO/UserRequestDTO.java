package hansung.ElderCare.Server.dto.UserDTO;

import hansung.ElderCare.Server.domain.enums.Relationship;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

public class UserRequestDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserInfoEditDTO {

        @Schema(description = "보호자(사용자) 이름", example = "송진우")
        private String name;

        @Schema(description = "인증 이미지 파일 (multipart/form-data)", type = "string", format = "binary")
        private MultipartFile profileImage;

        @Schema(description = "전화번호", example = "01011112222")
        private String phoneNumber;

        @Schema(description = "보호대상자와의 관계", example = "PARENT")
        private String relationship;
    }

}
