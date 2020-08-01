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

    @Query(nativeQuery = true, value = "select t1.total,shareName,amount,shareCode from ( select sum(total) as total, share_name as shareName, -(sum(amount)) - sum(handFee) as amount ,share_code as shareCode from (          select trade_record.share_name,     trade_record.share_code    ,        trade_record.pay_type,                 CASE trade_record.pay_type                     WHEN 'SELL' THEN                         -sum(trade_record.pay_count)                     WHEN 'BUY' THEN                         sum(trade_record.pay_count)                     ELSE                         0                     END total,                 CASE trade_record.pay_type  WHEN 'SELL' THEN -sum(trade_record.pay_amount + trade_record.fee) WHEN 'BUY' THEN sum(trade_record.pay_amount - trade_record.fee) ELSE 0 END amount ,sum(fee) as handFee         from king.trade_record     where state = 1 and user_id = :userId      group by share_name, pay_type , share_code     ) t group by share_name,share_code )  t1 where t1.total <= :keepCount order by   amount desc ")
    List<TradeProfitCount> calculateProfit(@Param(value = "userId") long userId, @Param(value = "keepCount") double keepCount);

    @Query(nativeQuery = true, value = "select t1.total,shareName,amount,shareCode from ( select sum(total) as total, share_name as shareName, -(sum(amount)) - sum(handFee) as amount ,share_code as shareCode from (          select trade_record.share_name,     trade_record.share_code    ,        trade_record.pay_type,                 CASE trade_record.pay_type                     WHEN 'SELL' THEN                         -sum(trade_record.pay_count)                     WHEN 'BUY' THEN                         sum(trade_record.pay_count)                     ELSE                         0                     END total,                 CASE trade_record.pay_type  WHEN 'SELL' THEN -sum(trade_record.pay_amount + trade_record.fee) WHEN 'BUY' THEN sum(trade_record.pay_amount - trade_record.fee) ELSE 0 END amount ,sum(fee) as handFee         from king.trade_record     where state = 1 and user_id = :userId     group by share_name, pay_type , share_code     ) t group by share_name,share_code )  t1 where t1.total <= :keepCount order by   amount asc ")
    List<TradeProfitCount> calculateLoss(@Param(value = "userId") long userId, @Param(value = "keepCount") double keepCount);

    @Query(value = "select count(1) as total,type as shareName,0 as amount, type as shareCode from  (select case  when profit.profit >0 then 'profit' when profit.profit < 0 then 'loss' when profit.profit =0 then 'none' else  'none' end type from profit where state =1 and user_id = :userId)  t group by type order by type", nativeQuery = true)
    List<TradeProfitCount> profitLossCompare(@Param(value = "userId") long userId);

    @Query(value = "select  sum(profit.profit) as amount ,0 as total ,'' as shareName, '' as shareCode from profit where state =1 and user_id = :userId", nativeQuery = true)
    List<TradeProfitCount> profit(@Param(value = "userId") long userId);

    List<Profit> findByUserIdAndShareCodeAndState(long userId, String shareCode, int state);

    @Query(nativeQuery = true, value = "select t1.total,shareName,amount,shareCode from ( select sum(total) as total, share_name as shareName, -(sum(amount)) - sum(handFee) as amount ,share_code as shareCode from (          select trade_record.share_name,     trade_record.share_code    ,        trade_record.pay_type,                 CASE trade_record.pay_type                     WHEN 'SELL' THEN                         -sum(trade_record.pay_count)                     WHEN 'BUY' THEN                         sum(trade_record.pay_count)                     ELSE                         0                     END total,                 CASE trade_record.pay_type  WHEN 'SELL' THEN -sum(trade_record.pay_amount + trade_record.fee) WHEN 'BUY' THEN sum(trade_record.pay_amount - trade_record.fee) ELSE 0 END amount ,sum(fee) as handFee         from king.trade_record     where state = 1 and user_id = :userId  and share_code = :shareCode    group by share_name, pay_type , share_code     ) t group by share_name,share_code )  t1  order by   amount desc ")
    List<TradeProfitCount> calculateProfitByShareCode(@Param(value = "userId") long userId, @Param(value = "shareCode") String shareCode);


}
