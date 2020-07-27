package zhh.share.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import zhh.share.dao.TradeRecordRepository;
import zhh.share.entity.TradeRecord;
import zhh.share.pojo.TradeRecordCount;
import zhh.share.service.TradeRecordService;

import javax.persistence.criteria.Predicate;
import java.util.Date;
import java.util.List;

/**
 * @author richer
 * @date 2020/7/21 10:27 上午
 */
@Service
public class TradeRecordServiceImpl implements TradeRecordService {

    @Autowired
    TradeRecordRepository tradeRecordRepository;


    @Override
    public TradeRecord save(TradeRecord tradeRecord) {
        return tradeRecordRepository.save(tradeRecord);
    }

    @Override
    public List<TradeRecord> findAll() {
        return tradeRecordRepository.findAll();
    }

    @Override
    public Page<TradeRecord> findAll(Example<TradeRecord> example, Pageable pageable) {
        if (example == null) {
            return tradeRecordRepository.findAll(pageable);
        } else {
            return tradeRecordRepository.findAll(example, pageable);
        }
    }

    @Override
    public Page<TradeRecord> findAll(Specification<TradeRecord> specification, Pageable pageable) {
        return tradeRecordRepository.findAll(specification, pageable);
    }

    @Override
    public Page<TradeRecord> findByPage(int page, int size) {
        return tradeRecordRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Page<TradeRecord> findByAllProps(long userId, int page, int size, String name, String code, String payType, Date startTime, Date endTime, boolean orderByPayTimeAsc) {
        return tradeRecordRepository.findAll((Specification<TradeRecord>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (userId > 0) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get(TradeRecord.USER_ID), userId));
            }
            if (StringUtils.isNotBlank(name)) {
                predicate.getExpressions().add(criteriaBuilder.like(root.get(TradeRecord.SHARE_NAME), "%" + name + "%"));
            }
            if (StringUtils.isNotBlank(code)) {
                predicate.getExpressions().add(criteriaBuilder.like(root.get(TradeRecord.SHARE_CODE), "%" + code + "%"));
            }
            if (StringUtils.isNotBlank(payType)) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get(TradeRecord.PAY_TYPE), payType));
            }
            if (startTime != null) {
                predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get(TradeRecord.PAY_TIME), startTime));
            }
            if (endTime != null) {
                predicate.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(root.get(TradeRecord.PAY_TIME), endTime));
            }
            return predicate;
        }, PageRequest.of(page, size, orderByPayTimeAsc ? Sort.Direction.ASC : Sort.Direction.DESC, TradeRecord.PAY_TIME));
    }

    @Override
    public int saveAll(List<TradeRecord> tradeRecords) {
        return tradeRecordRepository.saveAll(tradeRecords).size();
    }

    @Override
    public long countByShareName(String shareName) {
        return tradeRecordRepository.countByShareName(shareName);
    }

    @Override
    public List<TradeRecordCount> groupByShareName() {
        return tradeRecordRepository.groupByShareName();
    }

}
