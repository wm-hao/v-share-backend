package zhh.share.constant;

/**
 * @author richer
 * @date 2020/7/21 4:21 下午
 */
public class CommonConstant {

    public interface Message {
        String QRY_SUCCESS = "查询成功";
        String ADD_NEW_USER = "新增用户成功";
        String IMPORT_TRADE_RECORD = "通过表格文件导入交易记录成功";
        String ADD_NEW_RECORD = "新增记录成功";
        String INFO_EMPTY = "信息不能为空";
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
    }

    public interface Order {
        String ASC = "ASC";
        String DESC = "DESC";
    }

    public interface Reverse {
        String YES = "Y";
    }
}
