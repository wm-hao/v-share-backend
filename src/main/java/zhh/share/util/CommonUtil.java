package zhh.share.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import zhh.share.constant.CommonConstant;
import zhh.share.dto.BaseRequest;
import zhh.share.dto.BaseResponse;

import java.util.Random;

/**
 * @author richer
 * @date 2020/7/21 4:22 下午
 */
public class CommonUtil {

    private static final String SALT = "abc123";

    public static String[] verificationCodes = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };

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

    /**
     * 生成指定长度的验证码
     */
    public static String createVerificationCode(int verificationCodeLength) {
        StringBuilder verificationCode = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < verificationCodeLength; i++) {
            verificationCode.append(verificationCodes[random.nextInt(verificationCodes.length)]);
        }
        return verificationCode.toString();
    }


    public static String getMD5(String plainText) {
        if (StringUtils.isNoneBlank(plainText)) {
            return DigestUtils.md5Hex(SALT + plainText);
        }
        return null;
    }

}
