package hansung.ElderCare.Server.controller.specification;

import hansung.ElderCare.Server.apiPayload.ApiResponse;
import hansung.ElderCare.Server.dto.UserDTO.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;

public interface UserSpecification {

    @GetMapping
    @Operation(summary = "사용자의 정보 조회", description = "사용자의 계정 정보를 조회합니다.")
    public ApiResponse<UserResponseDTO> getUsers();
}
