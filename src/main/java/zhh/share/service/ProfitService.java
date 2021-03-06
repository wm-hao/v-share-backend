package zhh.share.service;

import org.springframework.data.domain.Page;
import zhh.share.entity.Profit;
import zhh.share.pojo.TradeProfitCount;

import java.util.List;

/**
 * @author richer
 * @date 2020/7/23 11:31 上午
 */
public interface ProfitService {

    void saveAll(List<Profit> profits);

    void generateProfitAll(long userId) throws Exception;

    List<TradeProfitCount> calculateTradeProfit(long userId);

    List<TradeProfitCount> calculateTradeLoss(long userId);

    List<TradeProfitCount> profitLossCompare(long userId);

    List<TradeProfitCount> profit(long userId);

    Page<Profit> pagination(long userId, int page, int size, boolean asc, String shareName, String showKeep);

    void calculateProfit(long userId, String shareCode) throws Exception;
}
