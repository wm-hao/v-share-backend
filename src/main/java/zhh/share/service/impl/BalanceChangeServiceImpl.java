package zhh.share.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhh.share.constant.CommonConstant;
import zhh.share.dao.BalanceChangeRepository;
import zhh.share.entity.BalanceChange;
import zhh.share.service.BalanceChangeService;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author richer
 * @date 2020/7/26 9:44 上午
 */
@Service
public class BalanceChangeServiceImpl implements BalanceChangeService {

    @Autowired
    BalanceChangeRepository balanceChangeRepository;


    @Override
    public List<BalanceChange> findByUserIdAndUpdateTime(long userId, Timestamp startDate, Timestamp endDate) {
        return balanceChangeRepository.findByUserIdAndStateAndUpdateTimeBetweenOrderByUpdateTime(userId, CommonConstant.State.STATE_VALID, startDate, endDate);
    }
}
