package zhh.share.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import zhh.share.entity.Profit;
import zhh.share.pojo.TradeProfitCount;

import java.util.List;

/**
 * @author richer
 * @date 2020/7/23 11:30 上午
 */
@Repository
public interface ProfitRepository extends JpaRepository<Profit, Long>, JpaSpecificationExecutor<Profit> {

    @Query(nativeQuery = true, value = "select t1.total,shareName,amount,shareCode from ( select sum(total) as total, share_name as shareName, -(sum(amount)) - sum(handFee) as amount ,share_code as shareCode from (          select trade_record.share_name,     trade_record.share_code    ,        trade_record.pay_type,                 CASE trade_record.pay_type                     WHEN 'SELL' THEN                         -sum(trade_record.pay_count)                     WHEN 'BUY' THEN                         sum(trade_record.pay_count)                     ELSE                         0                     END total,                 CASE trade_record.pay_type  WHEN 'SELL' THEN -sum(trade_record.pay_amount + trade_record.fee) WHEN 'BUY' THEN sum(trade_record.pay_amount - trade_record.fee) ELSE 0 END amount ,sum(fee) as handFee         from king.trade_record     where state = 1 and user_id = :userId      group by share_name, pay_type , share_code     ) t group by share_name,share_code )  t1 where t1.total <= :keepCount order by   amount desc " )
    List<TradeProfitCount> calculateProfit(@Param(value = "userId") long userId, @Param(value = "keepCount") double keepCount);

    @Query(nativeQuery = true, value = "select t1.total,shareName,amount,shareCode from ( select sum(total) as total, share_name as shareName, -(sum(amount)) - sum(handFee) as amount ,share_code as shareCode from (          select trade_record.share_name,     trade_record.share_code    ,        trade_record.pay_type,                 CASE trade_record.pay_type                     WHEN 'SELL' THEN                         -sum(trade_record.pay_count)                     WHEN 'BUY' THEN                         sum(trade_record.pay_count)                     ELSE                         0                     END total,                 CASE trade_record.pay_type  WHEN 'SELL' THEN -sum(trade_record.pay_amount + trade_record.fee) WHEN 'BUY' THEN sum(trade_record.pay_amount - trade_record.fee) ELSE 0 END amount ,sum(fee) as handFee         from king.trade_record     where state = 1 and user_id = :userId     group by share_name, pay_type , share_code     ) t group by share_name,share_code )  t1 where t1.total <= :keepCount order by   amount asc " )
    List<TradeProfitCount> calculateLoss(@Param(value = "userId") long userId, @Param(value = "keepCount") double keepCount);
}
