package zhh.share.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import zhh.share.entity.Journal;

/**
 * @author richer
 * @date 2020/8/1 12:53 下午
 */
@Repository
public interface JournalRepository extends JpaSpecificationExecutor<Journal>, JpaRepository<Journal, Long> {
}
