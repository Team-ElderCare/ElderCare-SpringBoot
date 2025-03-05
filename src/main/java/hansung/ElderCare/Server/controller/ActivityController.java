package hansung.ElderCare.Server.controller;

import hansung.ElderCare.Server.apiPayload.code.status.ErrorStatus;
import hansung.ElderCare.Server.apiPayload.ApiResponse;
import hansung.ElderCare.Server.controller.specification.ActivitySpecification;
import hansung.ElderCare.Server.dto.ActivityDTO.ActivityRequestDTO;
import hansung.ElderCare.Server.dto.ActivityDTO.ActivityResponseDTO;
import hansung.ElderCare.Server.service.ActivityService.ActivityCommandService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Tag(name = "Activity", description = "활동 기록 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/activities")
public class ActivityController implements ActivitySpecification {

    private final ActivityCommandService activityCommandService;


    @Override
    @PostMapping("")
    public ApiResponse<?> addActivity(@Valid @RequestBody ActivityRequestDTO.AddActivityRequestDTO request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = validateHandling(bindingResult);

            return ApiResponse.onFailure(
                    ErrorStatus.ACTIVITY_DATA_UNSATISFIED.getCode(),
                    ErrorStatus.ACTIVITY_DATA_UNSATISFIED.getMessage(),
                    errorMap
            );
        }

        return ApiResponse.onSuccess(activityCommandService.addActivity(request));
    }


    private Map<String, String> validateHandling(BindingResult bindingResult) {
        Map<String, String> validatorResult = new HashMap<>();

        bindingResult.getFieldErrors().forEach(error -> {
            validatorResult.put(error.getField(), error.getDefaultMessage());
        });

        return validatorResult;
    }
}
