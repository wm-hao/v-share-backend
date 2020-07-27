package zhh.share.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zhh.share.constant.CommonConstant;
import zhh.share.dto.BaseResponse;
import zhh.share.entity.User;
import zhh.share.service.UserService;
import zhh.share.util.CommonUtil;

/**
 * @author richer
 * @date 2020/7/23 5:55 下午
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    private static transient Log log = LogFactory.getLog(UserController.class);

    @Autowired
    UserService userService;

    @PostMapping("/add")
    public BaseResponse addNewUser(@RequestBody User user) {
        try {
            userService.addNewUser(user);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return CommonUtil.fail(e.getMessage());
        }
        return CommonUtil.success(CommonConstant.Message.ADD_NEW_USER);
    }
}
