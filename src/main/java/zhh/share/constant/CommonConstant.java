package zhh.share.constant;

/**
 * @author richer
 * @date 2020/7/21 4:21 下午
 */
public class CommonConstant {

    public interface Val {
        String ACCESS_TOKEN = "accessToken";
        String VERIFY_CODE = "verifyCode-";
    }

    public interface Message {
        String VERIFY_CODE_SUCCESS = "获取验证码成功,请查收,5分钟内有效";
        String VERIFY_CODE_FAIL = "获取验证码失败";
        String VALIDATE_SUCCESS = "验证成功";
        String UPDATE_SUCCESS = "更新成功";
        String ADD_SUCCESS = "添加成功";
        String QRY_SUCCESS = "查询成功";
        String ADD_NEW_USER = "新增用户成功";
        String IMPORT_TRADE_RECORD = "通过表格文件导入交易记录成功";
        String ADD_NEW_RECORD = "新增记录成功";
        String UPDATE_RECORD = "更新记录成功";
        String DELETE_RECORD = "删除记录成功";
        String INFO_EMPTY = "信息不能为空";
        String ADD_CUR_DAY_BALANCE = "记录当天账户资源成功";
        String QRY_CUR_DAY_BALANCE = "查询当天账户资源成功";
        String USER_EMPTY = "用户信息不存在";
        String USER_EMAIL_ERR = "用户信息不正确或用户邮箱不匹配";
    }

    public enum StatusCode {
        SUCCESS("SUCCESS", "成功"),
        FAIL("FAIL", "失败");

        private String code;
        private String desc;

        StatusCode(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }

        public String getCode() {
            return code;
        }
    }

    public interface State {
        int STATE_VALID = 1;
        int STATE_INVALID = -999;
    }

    public interface Order {
        String ASC = "ASC";
        String DESC = "DESC";
    }

    public interface Reverse {
        String YES = "Y";
    }

    public interface FrequencyType {
        String YEAR = "year";
        String MONTH = "month";
    }

    public interface Top {
        String PROFIT = "profit";
        String LOSS = "loss";
    }
}
