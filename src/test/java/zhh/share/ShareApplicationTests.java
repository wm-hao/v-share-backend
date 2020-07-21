package zhh.share;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import zhh.share.constant.ShareConstant;
import zhh.share.entity.TradeRecord;
import zhh.share.service.TradeRecordService;
import zhh.share.util.TimeUtil;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {ShareApplication.class})
public class ShareApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    TradeRecordService tradeRecordService;

    @Test
    public void testQryTradeRecord() throws ParseException {
        TradeRecord tradeRecord = new TradeRecord();
        tradeRecord.setPayAmount(600d);
        tradeRecord.setPayCount(200);
        tradeRecord.setUnitPrice(3.00d);
        tradeRecord.setCreateTime(TimeUtil.getCurrentTimestamp());
        tradeRecord.setUpdateTime(TimeUtil.getCurrentTimestamp());
        tradeRecord.setShareName("科达利1");
        tradeRecord.setShareCode("002850");
        tradeRecord.setFee(5.20);
        tradeRecord.setPayTime(TimeUtil.getTimestampFromString("20200505112346", TimeUtil.TimeFormat.YYYYMMDDHIMMSS));
        tradeRecord.setPayType(ShareConstant.PayType.BUY.getType());
        tradeRecord.setStockExchange(ShareConstant.SocketExchange.ShangHai.getType());
        TradeRecord newTrade = new TradeRecord();
        BeanUtils.copyProperties(tradeRecord, newTrade);
        newTrade.setShareName("正邦科技");
        List<TradeRecord> tradeRecords = new ArrayList<>();
        tradeRecords.add(tradeRecord);
        tradeRecords.add(newTrade);
        tradeRecordService.saveAll(tradeRecords);
    }

    @Test
    public void testQryPage() throws Exception {
        Timestamp start = TimeUtil.getTimestampFromString("2020-05-09 11:23:46", TimeUtil.TimeFormat.YYYY_MM_DD_HH_MM_SS);
        Timestamp end = TimeUtil.getTimestampFromString("2020-05-20 11:23:46", TimeUtil.TimeFormat.YYYY_MM_DD_HH_MM_SS);
        Page<TradeRecord> tradeRecords = tradeRecordService.findByAllProps(0, 5, "天", null, null, start, end);
        for (TradeRecord tradeRecord : tradeRecords) {
            System.out.println(tradeRecord);
        }
    }

}
