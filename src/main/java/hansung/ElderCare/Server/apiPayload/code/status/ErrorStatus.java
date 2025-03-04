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


    // 보호대상자관련 에러
    PROTECTED_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "PROTECTED4001", "이미 보호대상자가 등록되어 있습니다."),
    PROTECTED_NULL(HttpStatus.BAD_REQUEST, "PROTECTED4002", "보호대상자 객체가 NULL 값입니다."),
    PROTECTED_NO_PHONE_NUMBER(HttpStatus.BAD_REQUEST, "PROTECTED4003", "보호대상자의 전화번호가 NULL 값입니다."),
    PROTECTED_DATA_UNSATISFIED(HttpStatus.BAD_REQUEST, "PROTECTED4004", "보호대상자 관련 데이터가 유효성 검사에서 실패했습니다."),

    // 사용자-보호대상자 간 관계 테이블 에러
    USER_NOT_IN_RELATIONAL(HttpStatus.BAD_REQUEST, "UAUDUP4001", "관계 테이블에 해당 사용자가 없습니다."),

    //이미지 관련 에러
    IMAGE_SIZE_OVER(HttpStatus.BAD_REQUEST, "IMAGE4001", "이미지의 최대 크기는 5MB입니다."),

    //-------------- Device관련
    //Device관련 에러
    DEVICE_KIND_MISMATCH(HttpStatus.BAD_REQUEST, "Device4001", "사용자에게 해당 기기종류가 존재하지않습니다."),
    DEVICE_NOT_REGISTERED_TO_USER(HttpStatus.BAD_REQUEST, "DEVICE4002", "사용자에게 등록된 해당 종류의 디바이스가 없습니다."),
    //허브관련 에러
    HUB_NOT_FOUND(HttpStatus.BAD_REQUEST, "HUB4001", "허브를 찾을수 없습니다")

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
