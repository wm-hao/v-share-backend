package zhh.share.constant;

/**
 * @author richer
 * @date 2020/7/21 4:21 下午
 */
public class CommonConstant {

    public interface Message {
        String QRY_SUCCESS = "查询成功";
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
}
