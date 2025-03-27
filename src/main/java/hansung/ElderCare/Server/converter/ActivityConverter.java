package hansung.ElderCare.Server.converter;

import hansung.ElderCare.Server.domain.Activity;
import hansung.ElderCare.Server.domain.Device;
import hansung.ElderCare.Server.domain.enums.DeviceKind;
import hansung.ElderCare.Server.dto.ActivityDTO.ActivityResponseDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
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

    public static ActivityResponseDTO.getActivityResponseDTO toDailyActivityResponseDTO(
            List<Activity> activities, Map<Long, DeviceKind> deviceKindMap, LocalDate date) {

        // 위치별로 활동 그룹화
        Map<String, List<Activity>> locationGroups = activities.stream()
                .collect(Collectors.groupingBy(Activity::getDetectedLocation));

        // 위치별 활동 정보 생성
        List<ActivityResponseDTO.getActivityResponseDTO.DailyLocationActivityDTO> locationActivities = new ArrayList<>();

        for (Map.Entry<String, List<Activity>> entry : locationGroups.entrySet()) {
            String location = entry.getKey();
            List<Activity> locationActivitiesList = entry.getValue();

            // 활동 디테일 정보 생성
            List<ActivityResponseDTO.ActivityDTO> details = locationActivitiesList.stream()
                    .map(activity -> toActivityDTO(activity, deviceKindMap.getOrDefault(activity.getId(), DeviceKind.SENSOR)))
                    .collect(Collectors.toList());

            // 시간 표시 형식 결정
            String timeDisplay;
            if (locationActivitiesList.size() == 1) {
                timeDisplay = formatTimeForDisplay(locationActivitiesList.get(0).getTime());
            } else {
                // 시작 시간과 종료 시간 찾기
                Activity firstActivity = locationActivitiesList.stream()
                        .min(Comparator.comparing(Activity::getTime))
                        .orElse(locationActivitiesList.get(0));

                Activity lastActivity = locationActivitiesList.stream()
                        .max(Comparator.comparing(Activity::getTime))
                        .orElse(locationActivitiesList.get(locationActivitiesList.size() - 1));

                timeDisplay = formatTimeForDisplay(firstActivity.getTime()) + " ~ " +
                        formatTimeForDisplay(lastActivity.getTime());
            }

            // 위치별 활동 정보 생성 및 추가
            ActivityResponseDTO.getActivityResponseDTO.DailyLocationActivityDTO locationActivity =
                    ActivityResponseDTO.getActivityResponseDTO.DailyLocationActivityDTO.builder()
                            .location(location)
                            .time(timeDisplay)
                            .detectionCount(locationActivitiesList.size())
                            .detatils(details)  // 오타 주의: details가 아니라 detatils로 필드가 정의됨
                            .build();

            locationActivities.add(locationActivity);
        }

        // 최종 응답 생성
        return ActivityResponseDTO.getActivityResponseDTO.builder()
                .activities(locationActivities)
                .build();
    }

    /**
     * 시간을 "오전/오후 HH:MM" 형식으로 변환
     */
    private static String formatTimeForDisplay(LocalDateTime dateTime) {
        String amPm = dateTime.getHour() < 12 ? "오전" : "오후";
        int hour = dateTime.getHour() % 12;
        if (hour == 0) hour = 12;

        return String.format("%s %d:%02d", amPm, hour, dateTime.getMinute());
    }





}