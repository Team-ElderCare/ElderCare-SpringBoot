package hansung.ElderCare.Server.dto.ProtectedDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ProtectedRequestDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    // 보호대상자 등록 요청 DTO
    public static class RegistrationDTO {

        @NotNull
        @Schema(description = "보호대상자 이름")
        private String name;

        @NotNull
        @Schema(description = "생년월일")
        private LocalDate birthDate;

        @NotNull
        @Schema(description = "닉네임")
        private String nickname;

        @NotNull
        @Schema(description = "전화번호")
        private String phoneNumber;

        @NotNull
        @Schema(description = "보호대상자 프로필 사진")
        private String protectedImageUrl;

        @NotNull
        @Schema(description = "주소(우편번호, 건물명, 상세주소)")
        private AddressDTO address;

        @Getter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class AddressDTO {
            @Schema(description = "우편번호")
            private String zipcode;

            @Schema(description = "건물명")
            private String building;

            @Schema(description = "상세주소")
            private String detailedAddress;
        }
    }
}
