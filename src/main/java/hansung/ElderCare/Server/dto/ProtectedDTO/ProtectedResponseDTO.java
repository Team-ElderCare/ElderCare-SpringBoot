package hansung.ElderCare.Server.dto.ProtectedDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

public class ProtectedResponseDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProtectedInfo {
        @NotNull
        @Schema(description = "보호대상자 이름", example = "홍길동")
        private String name;

        @NotNull
        @Schema(description = "생년월일", example = "1990-01-01")
        private LocalDate birthDate;

        @NotNull
        @Schema(description = "닉네임", example = "길동홍")
        private String nickname;

        @NotNull
        @Schema(description = "전화번호", example = "010-0000-0000")
        private String phoneNumber;

        @NotNull
        @Schema(description = "보호대상자 프로필 사진", example = "imageURL")
        private String protectedImageUrl;

        @NotNull
        @Schema(description = "주소(우편번호, 건물명, 상세주소)", examples = {"12345", "한성대학교", "서울시 성북구 ..."})
        private AddressDTO address;

        @Getter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class AddressDTO {
            @Schema(description = "우편번호", example = "12345")
            private String zipcode;

            @Schema(description = "건물명", example = "한성대학교")
            private String building;

            @Schema(description = "상세주소", example = "서울시 성북구 ...")
            private String detailedAddress;
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class protectedPhoneNumber {
        @Schema(description = "전화번호", example = "010-0000-0000")
        private String phoneNumber;
    }
}
