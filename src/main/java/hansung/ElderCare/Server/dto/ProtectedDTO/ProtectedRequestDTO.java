package hansung.ElderCare.Server.dto.ProtectedDTO;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class ProtectedRequestDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    // 보호대상자 등록 요청 DTO
    public static class RegistrationDTO {

        @NotBlank(message = "이름은 필수 입력입니다.")
        @Schema(description = "보호대상자 이름", example = "홍길동")
        private String name;

        @NotBlank(message = "생년월일은 필수 입력입니다.")
        @Schema(description = "생년월일", example = "1990-01-01")
        private LocalDate birthDate;

        @NotBlank(message = "닉네임은 필수 입력입니다.")
        @Schema(description = "닉네임", example = "길동홍")
        private String nickname;

        @NotBlank(message = "전화번호는 필수 입력입니다.")
        @Schema(description = "전화번호", example = "010-0000-0000")
        private String phoneNumber;

        // 보호대상자 프로필 사진은 NULL 값 허용
        @Schema(description = "보호대상자 프로필 사진", example = "imageURL")
        private String protectedImageUrl;

        @NotBlank(message = "주소는 필수 입력 사항입니다.")
        @Schema(description = "주소(우편번호, 건물명, 상세주소)", examples = {"12345", "한성대학교", "서울시 성북구 ..."})
        private ProtectedResponseDTO.ProtectedInfo.AddressDTO address;

        @Getter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class AddressDTO {

            @NotBlank(message = "우편번호는 필수 입력 항목입니다.")
            @Schema(description = "우편번호", example = "12345")
            private String zipcode;

            @NotBlank(message = "건물명은 필수 입력 항목입니다.")
            @Schema(description = "건물명", example = "한성대학교")
            private String building;

            @NotBlank(message = "상세주소는 필수 입력 항목입니다.")
            @Schema(description = "상세주소", example = "서울시 성북구 ...")
            private String detailedAddress;
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProtectedHealthInfo {

        @NotNull(message = "키는 필수 입력 항목입니다.")
        @Schema(description = "키", example = "169")
        // 자료형을 int로 설정하면 height라는 프로퍼티가 JSON에 들어가 있지 않아도 기본값 0으로 넘어오기 때문에 유효성 검사 적용 X
        // Null값 체크를 위해선 Integer 적용해야함
        private Integer height;

        @NotNull(message = "몸무게는 필수 입력 항목입니다.")
        @Schema(description = "몸무게", example = "60")
        private Integer weight;

        @NotBlank(message = "혈액형은 필수 입력 항목입니다.")
        @Schema(description = "혈액형", example = "RH-A")
        private String bloodType;

        // boolean은 @NotNull 적용해도 의미없음
        @Schema(description = "알레르기 질환 유무", example = "true")
        private boolean isAllergy;

        // isAllergy 값에 따라 NULL 허용 여부 다름
        @ArraySchema(schema = @Schema(description = "알레르기 목록", example = "[\"집가고싶어알레르기\", \"페니실린 알레르기\"]"))
        private List<String> allergies;

        @Schema(description = "백신 접종 유무", example = "true")
        private boolean isVaccinated;

        // isVaccinated 값에 따라 NULL 허용 여부 다름
        @ArraySchema(schema = @Schema(description = "백신 목록", example = "[\"코로나19\", \"독감 예방주사\"]"))
        private List<String> vaccines;

        @Schema(description = "수술 경험 유무", example = "true")
        private boolean isSurgery;

        // isSurgery 값에 따라 NULL 허용 여부 다름
        @ArraySchema(schema = @Schema(description = "수술 목록", example = "[\"다이어트 수술\", \"맹장 수술\"]"))
        private List<String> surgeries;
    }
}
