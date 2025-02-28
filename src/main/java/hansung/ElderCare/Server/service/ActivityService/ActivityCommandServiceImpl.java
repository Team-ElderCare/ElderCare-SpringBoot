package hansung.ElderCare.Server.service.ActivityService;

import hansung.ElderCare.Server.apiPayload.code.status.ErrorStatus;
import hansung.ElderCare.Server.apiPayload.exception.HubHandler;
import hansung.ElderCare.Server.apiPayload.exception.ProtectedHandler;
import hansung.ElderCare.Server.apiPayload.exception.UA_UD_UPHandler;
import hansung.ElderCare.Server.apiPayload.exception.UserHandler;
import hansung.ElderCare.Server.converter.ActivityConverter;
import hansung.ElderCare.Server.domain.*;
import hansung.ElderCare.Server.domain.enums.DeviceKind;
import hansung.ElderCare.Server.dto.ActivityDTO.ActivityRequestDTO;
import hansung.ElderCare.Server.dto.ActivityDTO.ActivityResponseDTO;
import hansung.ElderCare.Server.repository.ActivityRepository;
import hansung.ElderCare.Server.repository.HubRepository;
import hansung.ElderCare.Server.repository.ProtectedRepository;
import hansung.ElderCare.Server.repository.UA_UD_UPRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ActivityCommandServiceImpl implements ActivityCommandService {

    private final ActivityRepository activityRepository;
    private final HubRepository hubRepository;
    private final ProtectedRepository protectedRepository;
    private final UA_UD_UPRepository uaUdUpRepository;

    @Override
    public ActivityResponseDTO.ActivityDTO addActivity(ActivityRequestDTO.AddActivityRequestDTO request){

        log.info(String.valueOf(request));

        // 1. hubCode와 clientCode로 Hub와 User 찾기
        Hub hub = hubRepository.findByHubCodeAndClientCodeWithUser(request.getHubCode(), request.getClientCode())
                .orElseThrow(() -> new HubHandler(ErrorStatus.HUB_NOT_FOUND));

        // Hub 코드와 클라이언트 코드로 바로 Protected 조회
        Protected protected_entity = protectedRepository.findByHubCodeAndClientCode(
                        request.getHubCode(), request.getClientCode())
                .orElseThrow(() -> new ProtectedHandler(ErrorStatus.PROTECTED_NULL));

        // 3. 현재 시간 포맷팅
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // 4. Activity 엔티티 생성 및 저장
        Activity activity = Activity.builder()
                .time(currentTime)
                .detectedLocation(request.getLocation())
                .protectedId(protected_entity)
                .device(hub)
                .build();

        Activity savedActivity = activityRepository.save(activity);

        return ActivityConverter.toActivityDTO(savedActivity, DeviceKind.valueOf(request.getDeviceKind()));
    }

}
