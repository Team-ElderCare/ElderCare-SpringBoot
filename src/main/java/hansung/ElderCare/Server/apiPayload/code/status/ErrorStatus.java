package hansung.ElderCare.Server.apiPayload.code.status;

import hansung.ElderCare.Server.apiPayload.code.BaseErrorCode;
import hansung.ElderCare.Server.apiPayload.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    //사용자관련 에러
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "USER4001", "해당하는 사용자가 없습니다."),

//    //멤버 관련 에러
//    NICKNAME_NOT_EXIST(HttpStatus.BAD_REQUEST, "USER4001", "비밀번호 확인이 일치하지 않습니다."),
//    USER_NOT_FOUND(HttpStatus.UNAUTHORIZED, "USER4002", "이메일 또는 패스워드가 일치하지 않습니다."),
//    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "USER4003", "이미 존재하는 이메일입니다."),
//    USER_STATUS_INACTIVE(HttpStatus.UNAUTHORIZED, "USER4004", "탈퇴한 회원입니다."),
//
//    //사용자 챌린지 관련 에러
//    USER_CHALLENGE_NOT_FOUND(HttpStatus.NOT_FOUND, "UC4001", "사용자 챌린지가 존재하지 않습니다"),
//    USER_CHALLENGE_COMPLETE(HttpStatus.BAD_REQUEST, "UC4002", "완료된 챌린지는 삭제가 불가합니다")
    ;


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}
