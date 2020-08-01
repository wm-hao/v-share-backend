package zhh.share.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import zhh.share.entity.Balance;
import zhh.share.pojo.BalanceCount;

import java.util.List;

/**
 * @author richer
 * @date 2020/7/26 8:14 下午
 */
@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> , JpaSpecificationExecutor<Balance> {

    List<Balance> findByUserIdAndStateOrderByCreateTime(long userId, int state);

    Balance findByDateAndStateAndUserId(String date, int state, Long userId);

    @Query(value = "select sum(profit) as profit, date from king.balance where user_id = :userId and state = 1 group by date ", nativeQuery = true)
    List<BalanceCount> qryProfitGroupByDate(@Param(value = "userId") long userId);
}
