package zhh.share.constant;

/**
 * @author richer
 * @date 2020/7/21 11:24 上午
 */
public class ShareConstant {

    public enum PayType {
        BUY("BUY", "买入"),
        SELL("SELL", "卖出"),
        BONUS("BONUS", "股息入账"),
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

    public enum StockExchange {
        ShangHai("SH", "上海", "6"),
        ShenZhen("SZ", "深圳", "0");

        private String type;
        private String desc;
        private String codePrefix;

        StockExchange(String type, String desc, String codePrefix) {
            this.type = type;
            this.desc = desc;
            this.codePrefix = codePrefix;
        }

        public String getType() {
            return type;
        }

        public String getDesc() {
            return desc;
        }

        public String getCodePrefix() {
            return codePrefix;
        }
    }
}
