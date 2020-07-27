package zhh.share.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author richer
 */
public class HeaderInterceptor implements HandlerInterceptor {
    private static transient Log log = LogFactory.getLog(HeaderInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.error("头部拦截器:" + request.getRequestURL());
        if (request.getHeader(HttpHeaders.ORIGIN) != null) {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT, HEAD");
            response.addHeader("Access-Control-Allow-Headers", "Content-Type,AccessToken");
            response.addHeader("Access-Control-Max-Age", "3600");
        }
        return true;

    }
}
