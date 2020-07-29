package zhh.share.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import zhh.share.dto.BaseResponse;
import zhh.share.util.CommonUtil;

/**
 * @author richer
 * @date 2020/7/29 5:56 下午
 */
@ControllerAdvice

public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public BaseResponse handle(Exception e) {
        return CommonUtil.fail(e.getMessage());
    }
}
