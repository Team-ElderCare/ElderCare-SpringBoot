package hansung.ElderCare.Server.controller.specification;

import hansung.ElderCare.Server.apiPayload.ApiResponse;
import hansung.ElderCare.Server.dto.ActivityDTO.ActivityRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;


public interface ActivitySpecification {

    @PostMapping("")
    @Operation(summary = "활동기록 추가", description = "허브로부터 데이터를 전달받아 보호대상자의 활동기록을 추가합니다.")
    @ApiResponses(value = {

    })

    public ApiResponse<?> addActivity(ActivityRequestDTO.AddActivityRequestDTO request, BindingResult bindingResult);

}
