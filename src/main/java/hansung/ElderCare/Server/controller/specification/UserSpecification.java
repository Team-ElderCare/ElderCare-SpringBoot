package hansung.ElderCare.Server.controller.specification;

import hansung.ElderCare.Server.apiPayload.ApiResponse;
import hansung.ElderCare.Server.dto.UserDTO.UserRequestDTO;
import hansung.ElderCare.Server.dto.UserDTO.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

public interface UserSpecification {

    @GetMapping
    @Operation(summary = "사용자의 정보 조회", description = "사용자의 계정 정보를 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "사용자 정보 조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "사용자를 찾을 수 없음")
    })
    ApiResponse<UserResponseDTO.UserDTO> getUser();

    @PatchMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "사용자 정보 수정", description = "사용자의 계정 정보를 수정하는 api입니다.")
    public ApiResponse<UserResponseDTO.UserDTO> updateUser(
            @RequestPart(value = "data") @Valid @Parameter(description = "사용자 정보(JSON)") UserRequestDTO.UserInfoEditDTO request,
            @RequestPart(value = "profileImage", required = false) @Parameter(description = "프로필 이미지") MultipartFile profileImage);
}