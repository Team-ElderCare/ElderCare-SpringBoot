package hansung.ElderCare.Server.apiPayload.exception;

import hansung.ElderCare.Server.apiPayload.code.BaseErrorCode;

public class HubHandler extends GeneralException{
    public HubHandler(BaseErrorCode code) {super(code);}
}
