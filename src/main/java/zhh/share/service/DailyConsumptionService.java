package zhh.share.service;

import org.springframework.data.domain.Page;
import zhh.share.entity.DailyConsumption;
import zhh.share.pojo.Statistic;

import java.util.List;

/**
 * @author richer
 * @date 2020/8/13 5:14 下午
 */
public interface DailyConsumptionService {

    Page<DailyConsumption> findByUserId(long userId, int page, int size, boolean asc) throws Exception;

    DailyConsumption addNew(DailyConsumption dailyConsumption) throws Exception;

    List<Statistic>  groupByDate(long userId) throws Exception;
}
