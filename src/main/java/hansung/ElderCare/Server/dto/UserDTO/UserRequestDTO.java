package hansung.ElderCare.Server.dto.UserDTO;

import hansung.ElderCare.Server.domain.enums.Relationship;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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

        @NotBlank(message = "사용자이름은 필수 입력사항입니다.")
        @Schema(description = "보호자(사용자) 이름", example = "송진우")
        private String name;

        @NotBlank
        @Schema(description = "인증 이미지 파일 (multipart/form-data)", type = "string", format = "binary")
        private MultipartFile profileImage;

        @NotBlank(message = "전화번호는 필수 입력사항입니다.")
        @Schema(description = "전화번호", example = "01011112222")
        private String phoneNumber;

        @NotBlank(message = "보호 대상자와의 관계를 입력하세요")
        @Schema(description = "보호대상자와의 관계", example = "PARENT")
        private String relationship;
    }

}
