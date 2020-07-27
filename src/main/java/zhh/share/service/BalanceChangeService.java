package zhh.share.service;

import zhh.share.entity.BalanceChange;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author richer
 * @date 2020/7/26 9:44 上午
 */
public interface BalanceChangeService {

    List<BalanceChange> findByUserIdAndUpdateTime(long userId, Timestamp startDate, Timestamp endDate);
}
