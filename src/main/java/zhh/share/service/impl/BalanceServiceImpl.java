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
import zhh.share.entity.BaseEntity;
import zhh.share.service.BalanceService;

import javax.persistence.criteria.Predicate;
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
        }, PageRequest.of(page, size, asc ? Sort.Direction.ASC : Sort.Direction.DESC, BaseEntity.CREATE_TIME));
    }

    @Override
    public List<Balance> saveAll(List<Balance> balances) {
        return balanceRepository.saveAll(balances);
    }
}
