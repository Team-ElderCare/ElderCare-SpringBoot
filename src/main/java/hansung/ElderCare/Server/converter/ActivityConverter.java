package hansung.ElderCare.Server.converter;

import hansung.ElderCare.Server.domain.Activity;
import hansung.ElderCare.Server.domain.Device;
import hansung.ElderCare.Server.domain.enums.DeviceKind;
import hansung.ElderCare.Server.dto.ActivityDTO.ActivityResponseDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ActivityConverter {

    //활동기록 한개
    public static ActivityResponseDTO.ActivityDTO toActivityDTO(Activity activity, DeviceKind deviceKind) {
        return ActivityResponseDTO.ActivityDTO.builder()
                .activityId(activity.getId())
                .time(activity.getTime())
                .deviceKind(deviceKind)
                .build();
    }

    public static ActivityResponseDTO.ActivityListResponseDTO toActivityListResponseDTO(List<Activity> activities,
                                                                                        Map<Long, DeviceKind> deviceKindMap) {
        // 감지 위치별로 활동을 그룹화
        Map<String, List<Activity>> locationGroups = activities.stream()
                .collect(Collectors.groupingBy(Activity::getDetectedLocation));

        // 위치별 그룹 DTO 생성
        List<ActivityResponseDTO.LocationGroupDTO> locationGroupDTOs = new ArrayList<>();

        for (Map.Entry<String, List<Activity>> entry : locationGroups.entrySet()) {
            String location = entry.getKey();
            List<Activity> locationActivities = entry.getValue();

            // 해당 위치의 활동들을 DTO로 변환
            List<ActivityResponseDTO.ActivityDTO> activityDTOs = locationActivities.stream()
                    .map(activity -> toActivityDTO(activity, deviceKindMap.getOrDefault(activity.getId(), DeviceKind.SENSOR)))
                    .collect(Collectors.toList());


            // 위치 그룹 DTO 생성
            ActivityResponseDTO.LocationGroupDTO locationGroupDTO = ActivityResponseDTO.LocationGroupDTO.builder()
                    .location(location)
                    .activities(activityDTOs)
                    .build();

            locationGroupDTOs.add(locationGroupDTO);
        }

        // 최종 전체 응답 DTO 생성
        return ActivityResponseDTO.ActivityListResponseDTO.builder()
                .locationGroups(locationGroupDTOs)
                .build();
    }
}