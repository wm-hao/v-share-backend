package zhh.share.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import zhh.share.entity.User;

/**
 * @author richer
 * @date 2020/7/23 5:24 下午
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    User findByUserNameAndState(String userName, Integer state);
}
