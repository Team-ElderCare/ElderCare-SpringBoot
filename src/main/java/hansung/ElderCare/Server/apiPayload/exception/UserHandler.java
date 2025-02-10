package hansung.ElderCare.Server.apiPayload.exception;

import hansung.ElderCare.Server.apiPayload.code.BaseErrorCode;

public class UserHandler extends GeneralException {
    public UserHandler(BaseErrorCode code) { super(code);}
}
