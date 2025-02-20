package hansung.ElderCare.Server.apiPayload.exception;

import hansung.ElderCare.Server.apiPayload.code.BaseErrorCode;

public class UA_UD_UPHandler extends GeneralException{
    public UA_UD_UPHandler(BaseErrorCode code) {
        super(code);
    }
}
