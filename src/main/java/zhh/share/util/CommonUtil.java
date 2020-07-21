package zhh.share.util;

import zhh.share.constant.CommonConstant;
import zhh.share.dto.BaseRequest;
import zhh.share.dto.BaseResponse;

/**
 * @author richer
 * @date 2020/7/21 4:22 下午
 */
public class CommonUtil {

    public static BaseResponse success(String message) {
        return new BaseResponse(CommonConstant.StatusCode.SUCCESS.getCode(), message, CommonConstant.StatusCode.SUCCESS.getDesc());
    }

    public static BaseResponse fail(String message) {
        return new BaseResponse(CommonConstant.StatusCode.FAIL.getCode(), message, CommonConstant.StatusCode.FAIL.getDesc());
    }

    public static void convertPagination(BaseRequest baseRequest) {
        if (baseRequest != null) {
            if (baseRequest.getPage() < 0) {
                baseRequest.setPage(0);
            }
            if (baseRequest.getSize() <= 0) {
                baseRequest.setSize(1);
            }
        }
    }
}
