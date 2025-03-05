package hansung.ElderCare.Server.service.ActivityService;

import hansung.ElderCare.Server.apiPayload.code.status.ErrorStatus;
import hansung.ElderCare.Server.apiPayload.exception.*;
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
import lombok.ToString;
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
@ToString
@Transactional
public class ActivityCommandServiceImpl implements ActivityCommandService {

    private final ActivityRepository activityRepository;
    private final HubRepository hubRepository;
    private final ProtectedRepository protectedRepository;
    private final UA_UD_UPRepository uaUdUpRepository;

    @Override
    public ActivityResponseDTO.ActivityDTO addActivity(ActivityRequestDTO.AddActivityRequestDTO request){

        // hubCode와 clientCode로 Hub와 User 찾기
        Device device = hubRepository.findByHubCodeAndClientCode(request.getHubCode(), request.getClientCode())
                .orElseThrow(() -> new HubHandler(ErrorStatus.HUB_NOT_FOUND));


        // Device가 Hub 타입인지 확인하고 User 가져오기
        if (!(device instanceof Hub)) {
            throw new DeviceHandler(ErrorStatus.DEVICE_KIND_MISMATCH);
        }

        UA_UD_UP uaUdUp = uaUdUpRepository.findByDeviceId(device.getId())
                .orElseThrow(() -> new UA_UD_UPHandler(ErrorStatus.USER_NOT_IN_RELATIONAL));

        User user = uaUdUp.getUser();

        // 요청된 디바이스 종류 저장
        DeviceKind requestedDeviceKind = DeviceKind.valueOf(request.getDeviceKind());

        // UA_UD_UP 테이블에서 현재 사용자에게 할당된 요청된 종류의 디바이스가 있는지 확인
        boolean deviceExists = uaUdUpRepository.existsByUserIdAndDeviceDeviceKind(
                user.getId(), requestedDeviceKind);

        log.info("해당 디바이스가 존재여부 >>>> " + deviceExists);

        if (!deviceExists) {
            throw new DeviceHandler(ErrorStatus.DEVICE_NOT_REGISTERED_TO_USER);
        }


        // Hub 코드와 클라이언트 코드로 바로 Protected 조회
        Protected protected_entity = uaUdUp.getProtected();

        // 현재 시간 포맷팅
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // Activity 엔티티 생성 및 저장
        Activity activity = Activity.builder()
                .time(currentTime)
                .detectedLocation(request.getLocation())
                .protectedId(protected_entity)
                .device(device)
                .build();

        Activity savedActivity = activityRepository.save(activity);

        return ActivityConverter.toActivityDTO(savedActivity, DeviceKind.valueOf(request.getDeviceKind()));
    }

}
