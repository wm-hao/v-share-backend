package zhh.share.interceptor;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import zhh.share.constant.CommonConstant;
import zhh.share.service.UserService;
import zhh.share.util.CommonUtil;
import zhh.share.util.RedisUtil;
import zhh.share.util.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    private static transient Log log = LogFactory.getLog(TokenInterceptor.class);

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    UserService userSV;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(CommonConstant.Val.ACCESS_TOKEN);
        log.error("请求:url=" + request.getRequestURL() + ";token=" + token);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        if (StringUtils.isBlank(token)) {
            response.getWriter().println(JSON.toJSONString(CommonUtil.fail("无token信息")));
            return false;
        }
        String userName = TokenUtil.getUserName(token);
        if (StringUtils.isBlank(userName)) {
            response.getWriter().println(JSON.toJSONString(CommonUtil.fail("token信息有误")));
            return false;
        }
        if (userSV.findByUserName(userName) == null) {
            response.getWriter().println(JSON.toJSONString(CommonUtil.fail("token对应的用户不存在")));
            return false;
        }
        Object object = redisUtil.get(userName);
        if (object == null) {
            response.getWriter().println(JSON.toJSONString(CommonUtil.fail("token已过期，请重新登录")));
            return false;
        }
        if (!StringUtils.equals(token, object.toString())) {
            response.getWriter().println(JSON.toJSONString(CommonUtil.fail("token不正确")));
            return false;
        }
        return true;
    }
}
