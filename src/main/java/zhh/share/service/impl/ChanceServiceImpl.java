package zhh.share.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhh.share.constant.CommonConstant;
import zhh.share.dao.ChanceRepository;
import zhh.share.entity.Chance;
import zhh.share.service.ChanceService;
import zhh.share.util.CommonUtil;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author richer
 * @date 2020/8/19 4:24 下午
 */
@Service
public class ChanceServiceImpl implements ChanceService {

    @Autowired
    ChanceRepository chanceRepository;

    @Override
    public List<Chance> findAll(long userId) throws Exception {
        return chanceRepository.findByUserIdAndStateOrderByUpdateTimeDesc(userId, CommonConstant.State.STATE_VALID);
    }

    @Override
    public void saveAll(List<Chance> chances) throws Exception {
        for (Chance chance : chances) {
            CommonUtil.fillDefaultProps(chance);
        }
        chanceRepository.saveAll(chances);
    }

    @Override
    public List<Chance> findAllAfterCreateTime(long userId, Timestamp createTime) throws Exception {
        return chanceRepository.findByUserIdAndStateAndCreateTimeAfter(userId, CommonConstant.State.STATE_VALID, createTime);
    }

    @Override
    public Chance add(Chance chance) throws Exception {
        CommonUtil.fillDefaultProps(chance);
        return chanceRepository.save(chance);
    }

    @Override
    public Chance update(Chance chance) throws Exception {
        return chanceRepository.save(chance);
    }
}
