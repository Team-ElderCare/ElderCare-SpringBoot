package hansung.ElderCare.Server.controller.specification;

import hansung.ElderCare.Server.apiPayload.ApiResponse;
import hansung.ElderCare.Server.dto.ActivityDTO.ActivityRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;


public interface ActivitySpecification {

    @PostMapping("")
    @Operation(summary = "활동기록 추가", description = "허브로부터 데이터를 전달받아 보호대상자의 활동기록을 추가합니다.")
    @ApiResponses(value = {
    })
    public ApiResponse<?> addActivity(ActivityRequestDTO.AddActivityRequestDTO request, BindingResult bindingResult);



    @GetMapping("")
    @Operation(summary = "날짜별 활동기록 조회 API", description = "특정 날짜의 활동기록을 조회하는 API입니다. Query String으로 date 파라미터를 전달하면 해당 날짜에 기록된 활동들을 위치별로 그룹화하여 제공합니다. 각 위치별로 활동 시간과 감지 횟수, 상세 정보를 포함합니다.")
            @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "⭕ SUCCESS"),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "DATE4001", description = "❌ 유효하지 않은 날짜입니다.",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER4002", description = "❌ 사용자를 찾을 수 없습니다.",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PROTECTED4002", description = "❌ 보호대상자 정보가 없습니다.",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ACTIVITY4001", description = "❌ 해당 날짜에 활동기록이 없습니다.",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON400", description = "❌ BAD, 잘못된 요청",
            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
})
public ApiResponse<?> getActivities(
        @RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd")
        @Parameter(description = "조회할 날짜 (yyyy-MM-dd 형식)", example = "2024-12-17") LocalDate date);

}
