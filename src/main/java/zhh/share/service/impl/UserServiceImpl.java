package zhh.share.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhh.share.constant.CommonConstant;
import zhh.share.dao.UserRepository;
import zhh.share.entity.User;
import zhh.share.service.UserService;
import zhh.share.util.CommonUtil;
import zhh.share.util.TimeUtil;

/**
 * @author richer
 * @date 2020/7/23 5:26 下午
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User addNewUser(User user) throws Exception {
        if (user != null) {
            user.setCreateDate(TimeUtil.getCurrentTimestamp());
            user.setOptDate(TimeUtil.getCurrentTimestamp());
            user.setValidDate(TimeUtil.getCurrentTimestamp());
            user.setExpireDate(TimeUtil.getDefaultExpireDate());
            user.setState(CommonConstant.State.STATE_VALID);
            if (user.isEncryption()) {
                user.setPassword(CommonUtil.getMD5(user.getPassword()));
            }
        }
        return userRepository.save(user);
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserNameAndState(userName, CommonConstant.State.STATE_VALID);
    }

    @Override
    public boolean validateUser(User user) {
        User qryUser = this.findByUserName(user.getUserName());
        if (qryUser == null || qryUser.getState() != CommonConstant.State.STATE_VALID) {
            return false;
        }
        return StringUtils.equals(qryUser.getPassword(), CommonUtil.getMD5(user.getPassword()));
    }
}
