package zhh.share.startup;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author richer
 * @date 2020/7/23 4:53 下午
 */
@Component
@Order(value = 100)
public class CommandTwo implements CommandLineRunner {
    private static transient Log log = LogFactory.getLog(CommandTwo.class);

    @Override
    public void run(String... args) throws Exception {
        log.error("===========启动执行任务2===========");
    }
}
