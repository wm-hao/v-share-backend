package zhh.share.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import zhh.share.constant.CommonConstant;
import zhh.share.dto.BaseResponse;
import zhh.share.entity.User;
import zhh.share.service.UserService;
import zhh.share.util.CommonUtil;
import zhh.share.util.RedisUtil;
import zhh.share.util.TokenUtil;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    RedisUtil redisUtil;

    @Value("${tokenExpireHours}")
    int tokenExpireHours;

    @Value("${verifyCodeExpireMinutes}")
    int verifyCodeExpireMinutes;

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

    @PostMapping("/validate")
    public BaseResponse validateUser(@RequestBody User user) throws Exception {
        BaseResponse response = CommonUtil.success(CommonConstant.Message.VALIDATE_SUCCESS);
        User dbUser = userService.validateUser(user);
        if (dbUser != null) {
            List<User> users = new ArrayList<>();
            Object existToken = redisUtil.get(dbUser.getUserName());
            String token;
            if (existToken != null && StringUtils.isNotBlank(existToken.toString())) {
                token = existToken.toString();
            } else {
                token = TokenUtil.createJWT(-1, dbUser);
                redisUtil.set(dbUser.getUserName(), token, tokenExpireHours * 60 * 60);
            }
            response.setData(token);
            users.add(dbUser);
            response.setRows(users);
            return response;
        } else {
            throw new Exception("用户名或密码错误");
        }
    }

    @GetMapping("verifyCode")
    public BaseResponse getVerifyCode(@RequestParam String userName, @RequestParam String email) throws Exception {
        if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(email)) {
            User user = userService.findByUserName(userName.trim());
            if (user != null && StringUtils.equals(email.trim(), user.getEmail())) {
                String verifyCode = CommonUtil.createVerificationCode(5);
                BaseResponse response = CommonUtil.success(CommonConstant.Message.VERIFY_CODE_SUCCESS);
                log.error("==================获取验证码============" + verifyCode);
                redisUtil.set(CommonConstant.Val.VERIFY_CODE + userName, verifyCode, verifyCodeExpireMinutes * 60);
                response.setData(verifyCode);
                return response;
            } else {
                throw new Exception(CommonConstant.Message.USER_EMAIL_ERR);
            }
        } else {
            throw new Exception(CommonConstant.Message.INFO_EMPTY);
        }
    }

    @PostMapping("/updatePass")
    public BaseResponse updatePassword(@RequestBody User user) throws Exception {
        Object dbVerifyCode = redisUtil.get(CommonConstant.Val.VERIFY_CODE + user.getUserName());
        if (dbVerifyCode != null && dbVerifyCode.toString().equals(user.getVerifyCode())) {
            user.setPassword(CommonUtil.getMD5(user.getPassword()));
            user.setEncryption(true);
            userService.updatePassword(user);
        } else {
            throw new Exception("验证码不正确");
        }
        return CommonUtil.success(CommonConstant.Message.UPDATE_SUCCESS);
    }

    @PostMapping("/logout")
    public BaseResponse logout(@RequestHeader HttpHeaders httpHeaders, @RequestBody User user) throws Exception {
        Object token = httpHeaders.get(CommonConstant.Val.ACCESS_TOKEN);
        if (token != null) {
            String userName = TokenUtil.getUserName(token.toString().replace("[", "").replace("]", ""));
            User dbUser = userService.findByUserName(userName);
            if (dbUser != null && dbUser.getId().equals(user.getId()) && redisUtil.get(userName) != null) {
                redisUtil.del(userName);
            }
        } else {
            throw new Exception("用户退出失败");
        }
        return CommonUtil.success("用户退出成功");
    }
}
