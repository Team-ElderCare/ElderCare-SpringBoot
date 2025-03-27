package hansung.ElderCare.Server.dto.ActivityDTO;

import hansung.ElderCare.Server.domain.enums.DeviceKind;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class ActivityResponseDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ActivityDTO {
        @Schema(description = "활동기록 ID")
        private Long activityId;

        @Schema(description = "발생 시간")
        private LocalDateTime time;

        @Schema(description = "기기 종류")
        private DeviceKind deviceKind;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LocationGroupDTO {
        @Schema(description = "기기 위치")
        private String location;

        @Schema(description = "특정 위치 기기의 활동기록 리스트")
        private List<ActivityDTO> activities;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ActivityListResponseDTO {
        @Schema(description = "위치별 활동기록 리스트")
        private List<LocationGroupDTO> locationGroups;
    }


    // 날짜별 활동기록 조회 응답 dto
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getActivityResponseDTO {

        private List<DailyLocationActivityDTO> activities;

        @Getter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class DailyLocationActivityDTO {

            @Schema(description = "활동 위치", example = "약장|거실")
            private String location;

            @Schema(description = "활동 시간", example = "오전 8:00 ~ 오전 9:30")
            private String time;

            @Schema(description = "감지 횟수", example = "2")
            private int detectionCount;

            @Schema(description = "상세 활동 정보")
            private List<ActivityDTO> detatils;
        }
    }


}
