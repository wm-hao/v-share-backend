package zhh.share.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import zhh.share.constant.CommonConstant;
import zhh.share.dao.ProfitRepository;
import zhh.share.entity.Balance;
import zhh.share.entity.BaseEntity;
import zhh.share.entity.Profit;
import zhh.share.pojo.TradeProfitCount;
import zhh.share.service.ProfitService;
import zhh.share.util.TimeUtil;

import javax.persistence.criteria.Predicate;
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
                profits.add(createNewProfit(tradeProfitCount, userId));
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

    @Override
    public List<TradeProfitCount> profitLossCompare(long userId) {
        return profitRepository.profitLossCompare(userId);
    }

    @Override
    public List<TradeProfitCount> profit(long userId) {
        return profitRepository.profit(userId);
    }

    @Override
    public Page<Profit> pagination(long userId, int page, int size, boolean asc, String shareName, String showKeep) {
        return profitRepository.findAll((Specification<Profit>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            predicate.getExpressions().add(criteriaBuilder.equal(root.get(Balance.USER_ID), userId));
            predicate.getExpressions().add(criteriaBuilder.equal(root.get(BaseEntity.STATE), CommonConstant.State.STATE_VALID));
            if(StringUtils.isNotBlank(shareName)) {
                predicate.getExpressions().add(criteriaBuilder.like(root.get(Profit.SHARE_NAME), "%" + shareName + "%"));
            }
            if (StringUtils.isBlank(showKeep)) {
                predicate.getExpressions().add(criteriaBuilder.isNotNull(root.get(Profit.PROFIT)));
            }
            return predicate;
        }, PageRequest.of(page, size, asc ? Sort.Direction.ASC : Sort.Direction.DESC, Profit.PROFIT));
    }

    @Override
    public void calculateProfit(long userId, String shareCode) throws Exception {
        List<TradeProfitCount> tradeProfitCounts = profitRepository.calculateProfitByShareCode(userId, shareCode);
        if (tradeProfitCounts == null || tradeProfitCounts.isEmpty() || tradeProfitCounts.get(0) == null) {
            throw new Exception("未查询到交易记录，无法计算当前盈亏,股票代码:" + shareCode);
        }
        TradeProfitCount count = tradeProfitCounts.get(0);
        List<Profit> profits = profitRepository.findByUserIdAndShareCodeAndState(userId, shareCode, CommonConstant.State.STATE_VALID);
        Profit profit = null;
        if (profits != null && profits.size() > 0) {
            profit = profits.get(0);
            profit.setUpdateTime(TimeUtil.getCurrentTimestamp());
            profit.setKeepCount(count.getTotal());
            if (profit.getKeepCount() > 0) {
                profit.setKeepAmount(Math.abs(count.getAmount()));
            } else {
                profit.setProfit(count.getAmount());
            }
        } else {
            profit = createNewProfit(count, userId);
        }
        profitRepository.save(profit);
    }

    private Profit createNewProfit(TradeProfitCount tradeProfitCount, long userId) throws Exception {
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
        return profit;
    }

}
