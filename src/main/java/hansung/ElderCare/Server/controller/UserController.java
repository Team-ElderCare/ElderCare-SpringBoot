package hansung.ElderCare.Server.controller;


import hansung.ElderCare.Server.apiPayload.ApiResponse;
import hansung.ElderCare.Server.controller.specification.UserSpecification;
import hansung.ElderCare.Server.domain.User;
import hansung.ElderCare.Server.dto.UserDTO.UserRequestDTO;
import hansung.ElderCare.Server.dto.UserDTO.UserResponseDTO;
import hansung.ElderCare.Server.service.userService.UserCommandService;
import hansung.ElderCare.Server.service.userService.UserQueryService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "사용자 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController implements UserSpecification {

    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;

    @Override
    @GetMapping("/") //사용자 정보 조회
    public ApiResponse<UserResponseDTO.UserDTO> getUser() {

        Long userId = 1L; //userId 하드코딩해서 사용

        return ApiResponse.onSuccess(userQueryService.getUserInfo(userId));

    }

    @Override
    @PatchMapping("/") //사용자 정보 수정
    public ApiResponse<UserResponseDTO.UserDTO> updateUser(@RequestBody UserRequestDTO.UserInfoEditDTO request) {

        Long userId = 1L; //userId 하드코딩해서 사용

        UserResponseDTO.UserDTO result = userCommandService.updateUserInfo(userId,request);

        return ApiResponse.onSuccess(result); //변경 후 정보 리턴
    }


}
