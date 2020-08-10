package zhh.share.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import zhh.share.constant.CommonConstant;
import zhh.share.constant.ShareConstant;
import zhh.share.dto.BaseRequest;
import zhh.share.dto.BaseResponse;
import zhh.share.dto.Pagination;
import zhh.share.entity.BaseEntity;

import java.util.List;
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

    public static BaseResponse success(String message, List<Object> rows) {
        BaseResponse response = new BaseResponse(CommonConstant.StatusCode.SUCCESS.getCode(), message, CommonConstant.StatusCode.SUCCESS.getDesc());
        response.setRows(rows);
        response.setTotal(rows.size());
        return response;
    }

    public static BaseResponse fail(String message) {
        return new BaseResponse(CommonConstant.StatusCode.FAIL.getCode(), message, CommonConstant.StatusCode.FAIL.getDesc());
    }

    public static BaseResponse fail(String message, String code) {
        return new BaseResponse(code, message, CommonConstant.StatusCode.FAIL.getDesc());
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

    public static ShareConstant.StockExchange convertStockExchange(String stockExchange) {
        if (StringUtils.isNotBlank(stockExchange)) {
            if (stockExchange.startsWith(ShareConstant.StockExchange.ShangHai.getCodePrefix())) {
                return ShareConstant.StockExchange.ShangHai;
            } else if (stockExchange.startsWith(ShareConstant.StockExchange.ShenZhen.getCodePrefix())) {
                return ShareConstant.StockExchange.ShenZhen;
            }
        }
        return null;
    }

    public static double processDoubleNull(Double amount) {
        if (amount == null) {
            return 0d;
        }
        return amount;
    }

    public static void processPagination(Pagination pagination, int limit) {
        if (pagination != null) {
            if (pagination.getPage() < 0) {
                pagination.setPage(0);
            }
            if (pagination.getSize() <= 0) {
                pagination.setSize(1);
            }
            int start = Math.min(pagination.getPage() * pagination.getSize(), limit);
            int end = Math.min(start + pagination.getSize(), limit);
            pagination.setStart(start);
            pagination.setEnd(end);
        }
    }
    public static void fillDefaultProps(BaseEntity baseEntity) throws Exception {
        if (baseEntity != null) {
            baseEntity.setState(CommonConstant.State.STATE_VALID);
            baseEntity.setCreateTime(TimeUtil.getCurrentTimestamp());
            baseEntity.setUpdateTime(TimeUtil.getCurrentTimestamp());
            baseEntity.setDate(TimeUtil.getCurrentDay());
        }
    }
}
