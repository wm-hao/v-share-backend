package zhh.share.pojo;

/**
 * @author richer
 * @date 2020/7/22 5:52 下午
 */
public class TradeRecordCountBean implements TradeRecordCount {

    private String shareName;
    private long total;

    @Override
    public String getShareName() {
        return null;
    }

    @Override
    public long getTotal() {
        return 0;
    }

    @Override
    public String toString() {
        return "TradeRecordCountBean{" +
                "shareName='" + shareName + '\'' +
                ", total=" + total +
                '}';
    }
}
