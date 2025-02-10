package hansung.ElderCare.Server.controller;


import hansung.ElderCare.Server.apiPayload.ApiResponse;
import hansung.ElderCare.Server.controller.specification.UserSpecification;
import hansung.ElderCare.Server.dto.UserDTO.UserResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "사용자 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController implements UserSpecification {

    @Override
    @GetMapping("/")
    public ApiResponse<UserResponseDTO> getUsers() {



        return ApiResponse.onSuccess(null);

    }


}
