package hansung.ElderCare.Server.service.ActivityService;

import hansung.ElderCare.Server.apiPayload.code.status.ErrorStatus;
import hansung.ElderCare.Server.apiPayload.exception.GeneralException;
import hansung.ElderCare.Server.apiPayload.exception.HubHandler;
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

        // 1. hubCode와 clientCode로 Hub 찾기
        Hub hub = hubRepository.findByHubCodeAndClientCode(request.getHubCode(), request.getClientCode())
                .orElseThrow(() -> new HubHandler(ErrorStatus.HUB_NOT_FOUND));

        // Hub에서 User 정보 가져오기
        User user = hub.getUser();
        if (user == null) {
            throw new UserHandler(ErrorStatus.USER_NOT_FOUND);
        }

        // User의 ID로 UA_UD_UP 리스트 찾기
        List<UA_UD_UP> uaUdUpList = uaUdUpRepository.findByUser_Id(user.getId());
        if (uaUdUpList.isEmpty()) {
            throw new UA_UD_UPHandler(ErrorStatus.USER_NOT_IN_RELATIONAL);
        }

        // 첫 번째 Protected 사용 (실제로는 더 정교한 로직이 필요할 수 있음)
        Protected protected_entity = uaUdUpList.get(0).getProtected();

        // 3. 현재 시간 포맷팅
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // 4. Activity 엔티티 생성 및 저장
        Activity activity = new Activity(
                null, // ID는 자동 생성
                currentTime,
                request.getLocation(),
                protected_entity,
                hub
        );

        Activity savedActivity = activityRepository.save(activity);

        return ActivityConverter.toActivityDTO(savedActivity, DeviceKind.valueOf(request.getDeviceKind()));
    }

}
