package zhh.share.service;

import zhh.share.entity.User;

/**
 * @author richer
 * @date 2020/7/23 5:25 下午
 */

public interface UserService {

    User addNewUser(User user) throws Exception;

    User findByUserName(String userName);

    boolean validateUser(User user);
}
