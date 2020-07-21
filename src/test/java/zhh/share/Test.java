package zhh.share;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import zhh.share.dto.BaseRequest;

/**
 * @author richer
 * @date 2020/7/21 4:49 下午
 */
public class Test {

    private static transient Log log = LogFactory.getLog(Test.class);

    @org.junit.Test
    public void test() {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setSize(10);
        baseRequest.setPage(0);
        baseRequest.setShareName("天");
        log.error(JSON.toJSONString(baseRequest));
    }
}
