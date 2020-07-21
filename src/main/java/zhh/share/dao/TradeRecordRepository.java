package zhh.share.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import zhh.share.entity.TradeRecord;

/**
 * @author richer
 * @date 2020/7/21 10:21 上午
 */
@Repository
public interface TradeRecordRepository extends JpaRepository<TradeRecord, Long>, JpaSpecificationExecutor<TradeRecord> {
}
