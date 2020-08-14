package zhh.share.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import zhh.share.constant.CommonConstant;
import zhh.share.dao.BalanceRepository;
import zhh.share.entity.Balance;
import zhh.share.entity.BalanceChange;
import zhh.share.entity.BaseEntity;
import zhh.share.pojo.BalanceCount;
import zhh.share.service.BalanceService;
import zhh.share.util.TimeUtil;

import javax.persistence.criteria.Predicate;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author richer
 * @date 2020/7/26 8:18 下午
 */
@Service
public class BalanceServiceImpl implements BalanceService {

    @Autowired
    BalanceRepository balanceRepository;

    @Override
    public List<Balance> findByUserId(long userId) {
        return balanceRepository.findByUserIdAndStateOrderByCreateTime(userId, CommonConstant.State.STATE_VALID);
    }

    @Override
    public Balance save(Balance balance) {
        return balanceRepository.save(balance);
    }

    @Override
    public Page<Balance> findByUserIdPagination(long userId, int page, int size, boolean asc) {
        return balanceRepository.findAll((Specification<Balance>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            predicate.getExpressions().add(criteriaBuilder.equal(root.get(Balance.USER_ID), userId));
            return predicate;
        }, PageRequest.of(page, size, asc ? Sort.Direction.ASC : Sort.Direction.DESC, BaseEntity.DATE));
    }

    @Override
    public List<Balance> saveAll(List<Balance> balances) {
        return balanceRepository.saveAll(balances);
    }

    @Override
    public Balance findCurrentDayBalance(Long userId) throws Exception {
        return balanceRepository.findByDateAndStateAndUserId(TimeUtil.getCurrentDay(), CommonConstant.State.STATE_VALID, userId);
    }

    @Override
    public List<BalanceCount> qryProfitGroupByDate(long userId) {
        return balanceRepository.qryProfitGroupByDate(userId);
    }

    @Override
    public List<Balance> findByUserIdAndStateAndCreateTimeAfter(long userId, Timestamp createTime) throws Exception {
        return balanceRepository.findByUserIdAndStateAndCreateTimeAfter(userId, CommonConstant.State.STATE_VALID, createTime);
    }
}
