package zhh.share.service;

import zhh.share.entity.Chance;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author richer
 * @date 2020/8/19 4:21 下午
 */
public interface ChanceService {

    List<Chance> findAll(long userId) throws Exception;

    void saveAll(List<Chance> chances) throws Exception;

    List<Chance> findAllAfterCreateTime(long userId, Timestamp createTime) throws Exception;

    Chance add(Chance chance) throws Exception;

    Chance update(Chance chance) throws Exception;
}
