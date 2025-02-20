package hansung.ElderCare.Server.apiPayload.exception;

import hansung.ElderCare.Server.apiPayload.code.BaseErrorCode;

public class ProtectedHandler extends GeneralException{
    public ProtectedHandler(BaseErrorCode code) {
        super(code);
    }
}
