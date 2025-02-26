package hansung.ElderCare.Server.controller;


import hansung.ElderCare.Server.apiPayload.ApiResponse;
import hansung.ElderCare.Server.controller.specification.UserSpecification;
import hansung.ElderCare.Server.dto.UserDTO.UserRequestDTO;
import hansung.ElderCare.Server.dto.UserDTO.UserResponseDTO;
import hansung.ElderCare.Server.service.userService.UserCommandService;
import hansung.ElderCare.Server.service.userService.UserQueryService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Tag(name = "User", description = "사용자 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController implements UserSpecification {

    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;

    @Override
    @GetMapping("") //사용자 정보 조회
    public ApiResponse<UserResponseDTO.UserDTO> getUser() {

        Long userId = 1L; //userId 하드코딩해서 사용

        log.info("aaaaa");

        return ApiResponse.onSuccess(userQueryService.getUserInfo(userId));

    }

    @PatchMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<UserResponseDTO.UserDTO> updateUser(
            @RequestPart(value = "data") @Valid @Parameter UserRequestDTO.UserInfoEditDTO request,
            @RequestPart(value = "profileImage", required = false) @Parameter MultipartFile profileImage) {

        log.info("컨트롤러 작동: request={}, 이미지 존재={}", request.getName(),
                (profileImage != null && !profileImage.isEmpty()));

        Long userId = 1L;
        UserResponseDTO.UserDTO result = userCommandService.updateUserInfo(userId, request, profileImage);
        return ApiResponse.onSuccess(result);
    }


}
