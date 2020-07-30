package zhh.share.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query(value = "select sum(total) as total, share_name as shareName from (          select trade_record.share_name,                 trade_record.pay_type,                 CASE trade_record.pay_type                     WHEN 'SELL' THEN                         -sum(trade_record.pay_count)                     WHEN 'BUY' THEN                         sum(trade_record.pay_count)                     ELSE                         0                     END total          from king.trade_record   where state =1       group by share_name, pay_type      ) t group by share_name", nativeQuery = true)
    List<TradeRecordCount> groupByShareName();

    @Query(value = "select date,count(1) as total from trade_record where state = 1 and user_id = :userId group by date order by date", nativeQuery = true)
    List<TradeRecordCount> frequency(@Param(value = "userId") long userId);

    @Query(value = "select count(1) as total,share_name as shareName from trade_record where state = 1 and user_id = :userId group by share_name order by total desc", nativeQuery = true)
    List<TradeRecordCount> top(@Param(value = "userId") long userId);

}
