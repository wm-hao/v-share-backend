package zhh.share.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import zhh.share.entity.BalanceChange;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author richer
 * @date 2020/7/26 9:35 上午
 */
@Repository
public interface BalanceChangeRepository  extends JpaSpecificationExecutor<BalanceChange> , JpaRepository<BalanceChange, Long> {

    List<BalanceChange> findByUserIdAndStateAndUpdateTimeBetweenOrderByUpdateTime(long userId, int state, Timestamp startDate, Timestamp endDate);
}
