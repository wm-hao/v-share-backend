package zhh.share.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import zhh.share.entity.Balance;

import java.util.List;

/**
 * @author richer
 * @date 2020/7/26 8:14 下午
 */
@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> , JpaSpecificationExecutor<Balance> {

    List<Balance> findByUserIdAndStateOrderByCreateTime(long userId, int state);
}
