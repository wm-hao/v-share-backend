package zhh.share.service;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import zhh.share.entity.TradeRecord;
import zhh.share.pojo.TradeRecordCount;

import java.util.Date;
import java.util.List;

/**
 * @author richer
 * @date 2020/7/21 11:08 上午
 */
public interface TradeRecordService {

    TradeRecord save(TradeRecord tradeRecord);

    List<TradeRecord> findAll();

    Page<TradeRecord> findAll(Example<TradeRecord> example, Pageable pageable);

    Page<TradeRecord> findAll(Specification<TradeRecord> specification, Pageable pageable);

    Page<TradeRecord> findByPage(int page, int size);

    Page<TradeRecord> findByAllProps(long userId, int page, int size, String name, String code, String alias, String payType, Date startTime, Date endTime, boolean orderByPayTimeAsc);

    int saveAll(List<TradeRecord> tradeRecords);

    long countByShareName(String shareName);

    List<TradeRecordCount> groupByShareName();

    TradeRecord update(TradeRecord tradeRecord) throws Exception;

    List<TradeRecordCount> frequency(long userId);
}
