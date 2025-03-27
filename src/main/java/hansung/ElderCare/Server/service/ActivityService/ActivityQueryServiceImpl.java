package hansung.ElderCare.Server.service.ActivityService;

import hansung.ElderCare.Server.apiPayload.code.status.ErrorStatus;
import hansung.ElderCare.Server.apiPayload.exception.ProtectedHandler;
import hansung.ElderCare.Server.apiPayload.exception.UA_UD_UPHandler;
import hansung.ElderCare.Server.converter.ActivityConverter;
import hansung.ElderCare.Server.domain.Activity;
import hansung.ElderCare.Server.domain.Device;
import hansung.ElderCare.Server.domain.Protected;
import hansung.ElderCare.Server.domain.UA_UD_UP;
import hansung.ElderCare.Server.domain.enums.DeviceKind;
import hansung.ElderCare.Server.dto.ActivityDTO.ActivityResponseDTO;
import hansung.ElderCare.Server.repository.ActivityRepository;
import hansung.ElderCare.Server.repository.UA_UD_UPRepository;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.awt.SystemColor.info;

@Service
@RequiredArgsConstructor
@Slf4j
@ToString
@Transactional(readOnly = true)
public class ActivityQueryServiceImpl implements ActivityQueryService {

    private final ActivityRepository activityRepository;
    private final UA_UD_UPRepository udUpRepository;

    public ActivityResponseDTO.getActivityResponseDTO getDailyActivity(Long userId, LocalDate date){
        // 1. 사용자 - 보호대상자간의 관계 검색
        UA_UD_UP uaUdUp = udUpRepository.findByUserIdWithProtected(userId)
                .orElseThrow(() -> new UA_UD_UPHandler(ErrorStatus.USER_NOT_IN_RELATIONAL));

        // 2. UaUdUp에 저장된 prtected에 대한 정보를 통해 보호대상자 정보 가져옴
        Protected protectedPerson = uaUdUp.getProtected();
        if(protectedPerson == null)
            throw new ProtectedHandler(ErrorStatus.PROTECTED_NULL);


        // 3. 보호대상자Id : protectedId를 사용해 Activity들을 찾기 위함
        Long protectedId = protectedPerson.getId();

        // 4. 시간범위 지정 (전달받은 날짜 00시 ~ 24시)
        LocalDateTime startDateTime = date.atStartOfDay(); //해당날짜 00시
        LocalDateTime endDateTime = startDateTime.plusDays(1).minusSeconds(1);

        // 5. 해당시간범위의 활동기록들 검색
        List<Activity> activities = activityRepository.findByProtectedIdAndTimeBetween(protectedId, startDateTime, endDateTime)
                .orElse(Collections.emptyList()); //해당 시간에 활동이 없으면 빈 리스트 반환

        log.info("------------------------------------------- 5번 6번 사이 ----------");
        // 상세 정보 로깅
        activities.forEach(activity -> {
            log.info("Activity ID: {}, Time: {}, Location: {}, Protected Person ID: {}",
                    activity.getId(),
                    activity.getTime(),
                    activity.getDetectedLocation(),
                    activity.getProtectedPerson() != null ? activity.getProtectedPerson().getId() : "null"
            );

            try {
                // Device 정보도 로깅 (조심스럽게 접근)
                Device device = activity.getDevice();
                if (device != null) {
                    log.info("  Device ID: {}, Device Type: {}",
                            device.getId(),
                            device.getClass().getSimpleName());
                } else {
                    log.info("  Device: null");
                }
            } catch (Exception e) {
                log.error("  Error accessing device: {}", e.getMessage());
            }
        });

        // 6. Device 종류 매핑 : 단일 활동기록과 device를 매칭해야하기 때문
        Map<Long, DeviceKind> deviceKindMap =
                activities.stream()
                        .collect(Collectors.toMap(Activity::getId, activity -> activity.getDevice().getDeviceKind()));


        return ActivityConverter.toDailyActivityResponseDTO(activities, deviceKindMap, date);
    }
}
