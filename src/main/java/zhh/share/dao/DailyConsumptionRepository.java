package zhh.share.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import zhh.share.entity.Balance;
import zhh.share.entity.DailyConsumption;
import zhh.share.pojo.BalanceCount;
import zhh.share.pojo.Statistic;

import java.util.List;

/**
 * @author richer
 * @date 2020/8/13 5:12 下午
 */
@Repository
public interface DailyConsumptionRepository extends JpaSpecificationExecutor<DailyConsumption>, JpaRepository<DailyConsumption, Long> {

    List<DailyConsumption> findByUserIdAndStateOrderByCreateTimeDesc(long userId, int state);

    DailyConsumption findByDateAndStateAndUserId(String date, int state, Long userId);

    @Query(value = "select sum(amount) as amount, date from king.daily_consumption where user_id = :userId and state = 1 group by date order by date ", nativeQuery = true)
    List<Statistic> qryConsumptionGroupByDate(@Param(value = "userId") long userId);
}
