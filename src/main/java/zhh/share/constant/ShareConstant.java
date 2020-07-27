package zhh.share.constant;

/**
 * @author richer
 * @date 2020/7/21 11:24 上午
 */
public class ShareConstant {

    public enum PayType {
        BUY("BUY", "买入"),
        SELL("SELL", "卖出"),
        BONUS("BOUNS", "股息入账"),
        TAX("TAX", "股息红利税补缴");

        private String type;
        private String desc;

        PayType(String type, String desc) {
            this.type = type;
            this.desc = desc;
        }

        public String getType() {
            return type;
        }

        public String getDesc() {
            return desc;
        }
    }

    public enum SocketExchange {
        ShangHai("SH", "上海"),
        ShenZhen("SZ", "深圳");

        private String type;
        private String desc;

        SocketExchange(String type, String desc) {
            this.type = type;
            this.desc = desc;
        }

        public String getType() {
            return type;
        }

        public String getDesc() {
            return desc;
        }
    }
}
