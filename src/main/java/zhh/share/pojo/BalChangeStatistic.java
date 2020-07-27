package zhh.share.pojo;

import lombok.Data;

/**
 * @author richer
 * @date 2020/7/26 9:37 上午
 */
public interface BalChangeStatistic {

    String getChangeType();

    String getBalanceType();

    double getChangeAmount();
}
