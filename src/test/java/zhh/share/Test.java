package zhh.share;

import com.alibaba.fastjson.JSON;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import zhh.share.dto.BaseRequest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        String sql = "select sum(total), share_name\n" +
                "from (\n" +
                "         select trade_record.share_name,\n" +
                "                trade_record.pay_type,\n" +
                "                CASE trade_record.pay_type\n" +
                "                    WHEN 'SELL' THEN\n" +
                "                        -sum(trade_record.pay_count)\n" +
                "                    WHEN 'BUY' THEN\n" +
                "                        sum(trade_record.pay_count)\n" +
                "                    ELSE\n" +
                "                        0\n" +
                "                    END total\n" +
                "         from king.trade_record\n" +
                "         group by share_name, pay_type\n" +
                "     ) t\n" +
                "group by share_name";
        log.error(sql.replaceAll("\n", " ").replaceAll("\\s", " "));
        String sql2 = "select sum(total) as total, share_name as shareName, -sum(amount) amount\n" +
                "from (\n" +
                "         select trade_record.share_name,\n" +
                "                trade_record.pay_type,\n" +
                "                CASE trade_record.pay_type\n" +
                "                    WHEN 'SELL' THEN\n" +
                "                        -sum(trade_record.pay_count)\n" +
                "                    WHEN 'BUY' THEN\n" +
                "                        sum(trade_record.pay_count)\n" +
                "                    ELSE\n" +
                "                        0\n" +
                "                    END total,\n" +
                "                CASE trade_record.pay_type  WHEN 'SELL' THEN -sum(trade_record.pay_amount + trade_record.fee) WHEN 'BUY' THEN sum(trade_record.pay_amount - trade_record.fee) ELSE 0 END amount\n" +
                "         from king.trade_record\n" +
                "         group by share_name, pay_type\n" +
                "     ) t\n" +
                "group by share_name order by total desc , amount desc";
        log.error(sql2.replaceAll("\n", " "));
    }

    @org.junit.Test
    public void testJson() {
        String json = "[\n" +
                "    {\n" +
                "        \"billDataId\":13628216,\n" +
                "        \"billDataMonth\":\"20200616101542\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"billDataId\":13628197,\n" +
                "        \"billDataMonth\":\"20200616101542\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"billDataId\":13628203,\n" +
                "        \"billDataMonth\":\"20200616101542\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"billDataId\":13628259,\n" +
                "        \"billDataMonth\":\"20200616101543\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"billDataId\":13628192,\n" +
                "        \"billDataMonth\":\"20200616101542\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"billDataId\":13628225,\n" +
                "        \"billDataMonth\":\"20200616101542\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"billDataId\":13628212,\n" +
                "        \"billDataMonth\":\"20200616101542\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"billDataId\":13628236,\n" +
                "        \"billDataMonth\":\"20200616101542\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"billDataId\":13628250,\n" +
                "        \"billDataMonth\":\"20200616101542\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"billDataId\":13628249,\n" +
                "        \"billDataMonth\":\"20200616101542\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"billDataId\":13628194,\n" +
                "        \"billDataMonth\":\"20200616101542\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"billDataId\":13628207,\n" +
                "        \"billDataMonth\":\"20200616101542\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"billDataId\":13628241,\n" +
                "        \"billDataMonth\":\"20200616101542\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"billDataId\":13628228,\n" +
                "        \"billDataMonth\":\"20200616101542\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"billDataId\":13628243,\n" +
                "        \"billDataMonth\":\"20200616101542\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"billDataId\":13628251,\n" +
                "        \"billDataMonth\":\"20200616101543\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"billDataId\":13628222,\n" +
                "        \"billDataMonth\":\"20200616101542\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"billDataId\":13628205,\n" +
                "        \"billDataMonth\":\"20200616101542\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"billDataId\":13628201,\n" +
                "        \"billDataMonth\":\"20200616101542\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"billDataId\":13628255,\n" +
                "        \"billDataMonth\":\"20200616101543\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"billDataId\":13628217,\n" +
                "        \"billDataMonth\":\"20200616101542\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"billDataId\":13628231,\n" +
                "        \"billDataMonth\":\"20200616101542\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"billDataId\":13628239,\n" +
                "        \"billDataMonth\":\"20200616101542\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"billDataId\":13628252,\n" +
                "        \"billDataMonth\":\"20200616101543\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"billDataId\":13628213,\n" +
                "        \"billDataMonth\":\"20200616101542\"\n" +
                "    }\n" +
                "]";
        List<A> as = JSON.parseArray(json, A.class);
        StringBuffer buffer = new StringBuffer();
        for (A a : as) {
            buffer.append("'" + a.getBillDataId() + "',");
        }
        log.error(buffer.toString());
    }


    @org.junit.Test
    public void testSet() {
        Set<Long> accCodes = new HashSet();
        long a = 12420;
        long b = 12420;
        accCodes.add(a);
        accCodes.add(b);
        log.error(accCodes.size());
    }


}
