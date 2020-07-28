package zhh.share.pojo;

import lombok.Data;

/**
 * @author richer
 * @date 2020/7/22 5:52 下午
 */
@Data
public class TradeRecordCountBean implements TradeRecordCount {

    private String shareName;
    private long total;
    private String date;

}
