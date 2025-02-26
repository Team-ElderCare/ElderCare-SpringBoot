package hansung.ElderCare.Server.dto.UserDTO;

import hansung.ElderCare.Server.domain.enums.Relationship;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserRequestDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserInfoEditDTO {

        @NotBlank
        @Size(min = 2, max = 20, message = "이름은 2자 이상 20자 이하로 입력해주세요.")
        @Schema(description = "보호자(사용자) 이름", example = "송진우")
        private String name;

        @NotBlank
        @Pattern(regexp = "^\\d{10,11}$", message = "전화번호는 10-11자리 숫자만 입력 가능합니다.")
        @Schema(description = "전화번호", example = "01011112222")
        private String phoneNumber;

        @NotBlank
        @Pattern(regexp = "^(PARENT|CHILDREN|SIBLING|COUSIN|FRIEND|CAREGIVER|ETC)$",
                message = "관계는 PARENT, CHILDREN, SIBLING, COUSIN, FRIEND, CAREGIVER, ETC 중 하나여야 합니다.")
        @Schema(description = "보호대상자와의 관계", example = "PARENT")
        private String relationship;
    }

}
