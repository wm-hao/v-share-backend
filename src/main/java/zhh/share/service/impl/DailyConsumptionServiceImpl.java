package zhh.share.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import zhh.share.constant.CommonConstant;
import zhh.share.dao.DailyConsumptionRepository;
import zhh.share.entity.Balance;
import zhh.share.entity.BaseEntity;
import zhh.share.entity.DailyConsumption;
import zhh.share.pojo.Statistic;
import zhh.share.service.DailyConsumptionService;
import zhh.share.util.CommonUtil;

import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * @author richer
 * @date 2020/8/13 5:16 下午
 */
@Service
public class DailyConsumptionServiceImpl implements DailyConsumptionService {

    @Autowired
    DailyConsumptionRepository dailyConsumptionRepository;

    @Override
    public Page<DailyConsumption> findByUserId(long userId, int page, int size, boolean asc) throws Exception {
        return dailyConsumptionRepository.findAll((Specification<DailyConsumption>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            predicate.getExpressions().add(criteriaBuilder.equal(root.get(Balance.USER_ID), userId));
            predicate.getExpressions().add(criteriaBuilder.equal(root.get(Balance.STATE), CommonConstant.State.STATE_VALID));
            return predicate;
        }, PageRequest.of(page, size, asc ? Sort.Direction.ASC : Sort.Direction.DESC, BaseEntity.ID));
    }

    @Override
    public DailyConsumption addNew(DailyConsumption dailyConsumption) throws Exception {
        CommonUtil.fillDefaultProps(dailyConsumption);
        return dailyConsumptionRepository.save(dailyConsumption);
    }

    @Override
    public List<Statistic> groupByDate(long userId) throws Exception {
        return dailyConsumptionRepository.qryConsumptionGroupByDate(userId);
    }
}
