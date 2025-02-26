package hansung.ElderCare.Server.controller.specification;

import hansung.ElderCare.Server.apiPayload.ApiResponse;
import hansung.ElderCare.Server.dto.ActivityDTO.ActivityRequestDTO;
import hansung.ElderCare.Server.dto.ActivityDTO.ActivityResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


public interface ActivitySpecification {

    @PostMapping("")
    @Operation(summary = "활동기록 조회", description = "보호대상자의 활동기록을 조회합니다.")
    @ApiResponses(value = {

    })

    public ApiResponse<ActivityResponseDTO.ActivityDTO> addActivity(ActivityRequestDTO.AddActivityRequestDTO request);

}
