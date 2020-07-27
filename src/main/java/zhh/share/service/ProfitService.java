package zhh.share.service;

import zhh.share.entity.Profit;
import zhh.share.pojo.TradeProfitCount;

import java.util.List;

/**
 * @author richer
 * @date 2020/7/23 11:31 上午
 */
public interface ProfitService {

    void saveAll(List<Profit> profits);

    void generateProfitAll();

    List<TradeProfitCount> calculateTradeProfit();
}
