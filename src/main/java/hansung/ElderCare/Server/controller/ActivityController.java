package hansung.ElderCare.Server.controller;

import hansung.ElderCare.Server.apiPayload.code.status.ErrorStatus;
import hansung.ElderCare.Server.apiPayload.ApiResponse;
import hansung.ElderCare.Server.controller.specification.ActivitySpecification;
import hansung.ElderCare.Server.dto.ActivityDTO.ActivityRequestDTO;
import hansung.ElderCare.Server.dto.ActivityDTO.ActivityResponseDTO;
import hansung.ElderCare.Server.service.ActivityService.ActivityCommandService;
import hansung.ElderCare.Server.service.ActivityService.ActivityQueryService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Tag(name = "Activity", description = "활동 기록 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/activities")
public class ActivityController implements ActivitySpecification {

    private final ActivityCommandService activityCommandService;
    private final ActivityQueryService activityQueryService;


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

    @Override
    @GetMapping("")
    public ApiResponse<?> getActivities(
            @RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd")
            @Parameter(description = "조회할 날짜 (yyyy-MM-dd 형식)", example = "2025-03-06") LocalDate date){

        //userId 하드코딩으로 사용
        Long userId = 1L;

        ActivityResponseDTO.getActivityResponseDTO result = activityQueryService.getDailyActivity(userId, date);


        return ApiResponse.onSuccess(result);
    }


    //requestDTO 유효성검사 예외처리 메소드
    private Map<String, String> validateHandling(BindingResult bindingResult) {
        Map<String, String> validatorResult = new HashMap<>();

        bindingResult.getFieldErrors().forEach(error -> {
            validatorResult.put(error.getField(), error.getDefaultMessage());
        });

        return validatorResult;
    }
}
