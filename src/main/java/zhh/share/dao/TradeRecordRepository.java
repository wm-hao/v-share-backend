package zhh.share.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import zhh.share.entity.TradeRecord;
import zhh.share.pojo.TradeRecordCount;

import java.util.List;

/**
 * @author richer
 * @date 2020/7/21 10:21 上午
 */
@Repository
public interface TradeRecordRepository extends JpaRepository<TradeRecord, Long>, JpaSpecificationExecutor<TradeRecord> {

    long countByShareName(String shareName);

    @Query(value = "select sum(total) as total, share_name as shareName from (          select trade_record.share_name,                 trade_record.pay_type,                 CASE trade_record.pay_type                     WHEN 'SELL' THEN                         -sum(trade_record.pay_count)                     WHEN 'BUY' THEN                         sum(trade_record.pay_count)                     ELSE                         0                     END total          from king.trade_record          group by share_name, pay_type      ) t group by share_name", nativeQuery = true)
    List<TradeRecordCount> groupByShareName();

}
