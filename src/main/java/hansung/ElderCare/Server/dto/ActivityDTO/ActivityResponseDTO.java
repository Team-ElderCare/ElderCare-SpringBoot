package hansung.ElderCare.Server.dto.ActivityDTO;

import hansung.ElderCare.Server.domain.enums.DeviceKind;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
        private String time;

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
}
