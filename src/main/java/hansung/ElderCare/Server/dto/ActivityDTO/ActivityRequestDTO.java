package hansung.ElderCare.Server.dto.ActivityDTO;

import hansung.ElderCare.Server.domain.enums.DeviceKind;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class ActivityRequestDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddActivityRequestDTO {

        @NotBlank(message = "허브코드는 반드시 필요합니다.")
        @Schema(description = "api를 호출한 허브의 기기코드", example = "코드")
        private String hubCode;

        @NotBlank(message = "사용자의 계정코드가 필요합니다.")
        @Schema(description = "사용자id 식별용 계정코드", example = "코드")
        private String clientCode;

        @NotBlank(message = "기기가 설치된 위치가 필요합니다.")
        @Schema(description = "기기가 설치된 위치", example = "약장")
        private String location;

        @Pattern(regexp = "^(HUB|SENSOR|TAG)$")
        @Schema(description = "기기 타입")
        private String deviceKind;


    }

}
