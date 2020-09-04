package zhh.share.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import zhh.share.constant.CommonConstant;
import zhh.share.dao.JournalRepository;
import zhh.share.entity.Journal;
import zhh.share.entity.TradeRecord;
import zhh.share.service.JournalService;
import zhh.share.util.CommonUtil;
import zhh.share.util.TimeUtil;

import javax.persistence.criteria.Predicate;
import java.util.Optional;

/**
 * @author richer
 * @date 2020/8/1 12:55 下午
 */
@Service
public class JournalServiceImpl implements JournalService {

    @Autowired
    JournalRepository journalRepository;

    @Override
    public Page<Journal> findByUserId(long userId, int page, int size) {
        return journalRepository.findAll((Specification<Journal>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (userId > 0) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get(TradeRecord.USER_ID), userId));
            }
            predicate.getExpressions().add(criteriaBuilder.equal(root.get(TradeRecord.STATE), CommonConstant.State.STATE_VALID));
            return predicate;
        }, PageRequest.of(page, size, Sort.Direction.DESC, Journal.CREATE_TIME));
    }

    @Override
    public Journal addNewJournal(Journal journal) throws Exception {
        CommonUtil.fillDefaultProps(journal);
        return journalRepository.save(journal);
    }

    @Override
    public Journal updateJournal(Journal journal) throws Exception {
        Optional<Journal> qry = journalRepository.findById(journal.getId());
        if (qry.isPresent()) {
            journal.setUpdateTime(TimeUtil.getCurrentTimestamp());
            journalRepository.save(journal);
        } else {
            throw new Exception("未查询到对应日志，无法更新");
        }
        return journal;
    }
}
