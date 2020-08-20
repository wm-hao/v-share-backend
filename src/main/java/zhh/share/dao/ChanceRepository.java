package zhh.share.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import zhh.share.entity.Chance;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author richer
 * @date 2020/8/19 4:10 下午
 */
@Repository
public interface ChanceRepository extends JpaRepository<Chance, Long>, JpaSpecificationExecutor<Chance> {

    List<Chance> findByUserIdAndStateOrderByUpdateTimeDesc(long userId, int state);


    List<Chance> findByUserIdAndStateAndCreateTimeAfter(long userId, int state, Timestamp createTime);
}
