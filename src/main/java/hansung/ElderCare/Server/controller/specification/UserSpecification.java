package hansung.ElderCare.Server.controller.specification;

import hansung.ElderCare.Server.apiPayload.ApiResponse;
import hansung.ElderCare.Server.dto.UserDTO.UserRequestDTO;
import hansung.ElderCare.Server.dto.UserDTO.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;

public interface UserSpecification {

    @GetMapping
    @Operation(summary = "사용자의 정보 조회", description = "사용자의 계정 정보를 조회합니다.")
    public ApiResponse<UserResponseDTO.UserDTO> getUser();

    @PatchMapping
    @Operation(summary = "사용자 정보 수정", description = "사용자의 계정 정보르 수정하는 api입니다. \n relationship 선택지 : PARENT, CHILDREN, SIBLING, COUSIN, FRIEND, CAREGIVER, ETC")
    public ApiResponse<UserResponseDTO.UserDTO> updateUser(@RequestBody UserRequestDTO.UserInfoEditDTO request);
}
