package hse.coursework.socialnetworkthoughts.exception;

import hse.coursework.socialnetworkthoughts.enums.SeparatorEnum;

public class CommonRuntimeException extends RuntimeException {

    public CommonRuntimeException(Integer code, String message) {
        super(code + SeparatorEnum.COLON.getValue() + message);
    }

    public CommonRuntimeException(Integer code, String message, Throwable cause) {
        super(code + SeparatorEnum.COLON.getValue() + message, cause);
    }

    public CommonRuntimeException(Integer code, String message, Exception exception) {
        super(code + SeparatorEnum.COLON.getValue() + message, exception);
    }
}
