package hansung.ElderCare.Server.dto.ProtectedDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

public class ProtectedResponseDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProtectedInfo {
        @Schema(description = "보호대상자 이름", example = "홍길동")
        private String name;

        @Schema(description = "생년월일", example = "1990-01-01")
        private LocalDate birthDate;

        @Schema(description = "닉네임", example = "길동홍")
        private String nickname;

        @Schema(description = "전화번호", example = "010-0000-0000")
        private String phoneNumber;

        @Schema(description = "보호대상자 프로필 사진", example = "imageURL")
        private String protectedImageUrl;

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

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class protectedHealthInfo {
        @Schema(description = "키", example = "169")
        private Integer height;

        @Schema(description = "몸무게", example = "60")
        private Integer weight;

        @Schema(description = "혈액형", example = "RH+A")
        private String bloodType;

        @Schema(description = "알레르기", example = "[\"집가고싶어 알레르기\", \"페니실린 알레르기\", \"토마토 알러지\"]")
        private List<String> allergies;

        @Schema(description = "백신", example = "[\"코로나 19\", \"독감 예방주사\", \"백신3\"]")
        private List<String> vaccines;

        @Schema(description = "수술", example = "[\"다이어트 수술\", \"맹장 수술\", \"수술 3\"]")
        private List<String> surgeries;
    }
}
