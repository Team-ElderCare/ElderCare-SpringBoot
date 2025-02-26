package hansung.ElderCare.Server.controller;

import hansung.ElderCare.Server.apiPayload.ApiResponse;
import hansung.ElderCare.Server.controller.specification.ActivitySpecification;
import hansung.ElderCare.Server.dto.ActivityDTO.ActivityRequestDTO;
import hansung.ElderCare.Server.dto.ActivityDTO.ActivityResponseDTO;
import hansung.ElderCare.Server.service.ActivityService.ActivityCommandService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "Activity", description = "활동 기록 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/activities")
public class ActivityController implements ActivitySpecification {

    private final ActivityCommandService activityCommandService;



    @Override
    @PostMapping("")
    public ApiResponse<ActivityResponseDTO.ActivityDTO> addActivity(@RequestBody ActivityRequestDTO.AddActivityRequestDTO request) {


        return ApiResponse.onSuccess(activityCommandService.addActivity(request));
    }
}
