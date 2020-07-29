package zhh.share.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhh.share.constant.CommonConstant;
import zhh.share.dao.ProfitRepository;
import zhh.share.entity.Profit;
import zhh.share.pojo.TradeProfitCount;
import zhh.share.service.ProfitService;
import zhh.share.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author richer
 * @date 2020/7/23 11:32 上午
 */
@Service
public class ProfitServiceImpl implements ProfitService {

    @Autowired
    ProfitRepository profitRepository;

    @Override
    public void saveAll(List<Profit> profits) {
        profitRepository.saveAll(profits);
    }

    @Override
    public void generateProfitAll(long userId) throws Exception {
        List<TradeProfitCount> profitCounts = profitRepository.calculateProfit(userId, Double.MAX_VALUE);
        if (profitCounts != null && profitCounts.size() > 0) {
            List<Profit> profits = new ArrayList<>();
            for (TradeProfitCount tradeProfitCount : profitCounts) {
                Profit profit = new Profit();
                profit.setCreateTime(TimeUtil.getCurrentTimestamp());
                profit.setUpdateTime(TimeUtil.getCurrentTimestamp());
                profit.setKeepCount(tradeProfitCount.getTotal());
                if (profit.getKeepCount() > 0) {
                    profit.setKeepAmount(Math.abs(tradeProfitCount.getAmount()));
                } else {
                    profit.setProfit(tradeProfitCount.getAmount());
                }
                profit.setShareName(tradeProfitCount.getShareName());
                profit.setShareCode(tradeProfitCount.getShareCode());
                profit.setState(CommonConstant.State.STATE_VALID);
                profit.setDate(TimeUtil.getCurrentDay());
                profit.setUserId(userId);
                profits.add(profit);
            }
            if (profits.size() > 0) {
                profitRepository.saveAll(profits);
            }
        }
    }

    @Override
    public List<TradeProfitCount> calculateTradeProfit(long userId) {
        return profitRepository.calculateProfit(userId, 0d);
    }

    @Override
    public List<TradeProfitCount> calculateTradeLoss(long userId) {
        return profitRepository.calculateLoss(userId, 0d);
    }
}
