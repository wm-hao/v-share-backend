package zhh.share;

import com.alibaba.fastjson.JSON;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import zhh.share.constant.CommonConstant;
import zhh.share.constant.ShareConstant;
import zhh.share.dao.TradeRecordRepository;
import zhh.share.entity.Balance;
import zhh.share.entity.BalanceChange;
import zhh.share.entity.TradeRecord;
import zhh.share.entity.User;
import zhh.share.pojo.TradeProfitCount;
import zhh.share.pojo.TradeRecordCount;
import zhh.share.service.*;
import zhh.share.util.ExcelUtil;
import zhh.share.util.TimeUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {ShareApplication.class})
public class ShareApplicationTests {

    private static transient Log log = LogFactory.getLog(ShareApplicationTests.class);

    @Test
    public void contextLoads() {
    }

    @Autowired
    TradeRecordService tradeRecordService;

    @Autowired
    ProfitService profitService;

    @Autowired
    UserService userService;

    @Autowired
    BalanceChangeService balanceChangeService;

    @Autowired
    BalanceService balanceService;

    @Autowired
    TradeRecordRepository tradeRecordRepository;

    @Test
    public void testQryTradeRecord() throws Exception {
        TradeRecord tradeRecord = new TradeRecord();
        tradeRecord.setPayAmount(600d);
        tradeRecord.setPayCount(200d);
        tradeRecord.setUnitPrice(3.00d);
        tradeRecord.setCreateTime(TimeUtil.getCurrentTimestamp());
        tradeRecord.setUpdateTime(TimeUtil.getCurrentTimestamp());
        tradeRecord.setShareName("科达利1");
        tradeRecord.setShareCode("002850");
        tradeRecord.setFee(5.20);
        tradeRecord.setPayTime(TimeUtil.getTimestampFromString("20200505112346", TimeUtil.TimeFormat.YYYYMMDDHIMMSS));
        tradeRecord.setPayType(ShareConstant.PayType.BUY.getType());
        tradeRecord.setStockExchange(ShareConstant.StockExchange.ShangHai.getType());
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
        Page<TradeRecord> tradeRecords = tradeRecordService.findByAllProps(1L, 0, 5, "", "", "K", null, null, null, false);
        for (TradeRecord tradeRecord : tradeRecords) {
            System.out.println(tradeRecord);
        }
    }

    @Test
    public void testSaveFromXls() throws Exception {
        String fileName = "/Users/zhuhaohao/Documents/test.xlsx";
        List<TradeRecord> tradeRecords = ExcelUtil.parseExcel2TradeRecord(fileName);
        for (TradeRecord tradeRecord : tradeRecords) {
            log.error(tradeRecord);
            tradeRecord.setUserId(1L);
        }
        tradeRecordService.saveAll(tradeRecords);
    }

    @Test
    public void testQryGroupByShareName() throws Exception {
        List<TradeRecordCount> tradeRecordCounts = tradeRecordService.groupByShareName();
        List<String> keep = new ArrayList<>();
        for (TradeRecordCount count : tradeRecordCounts) {
            if (count.getTotal() != 0) {
                keep.add(count.getShareName());
            }
        }
        for (String keepName : keep) {
            log.error("持有的:" + keepName);
        }
        List<TradeRecordCount> tradeRecordCounts1 = tradeRecordService.frequency(1L);
        for (TradeRecordCount count : tradeRecordCounts1) {
            log.error(count.getDate() + "--" + count.getTotal());
        }
    }

    @Test
    public void testGenerateProfitAll() throws Exception {
        profitService.generateProfitAll(1L);
    }

    @Test
    public void testCalculateProfit() {
        List<TradeProfitCount> calculateTradeProfitDesc = profitService.calculateTradeProfit(1L);
        List<TradeProfitCount> calculateTradeProfitAsc = profitService.calculateTradeLoss(1L);
        int i = 0;
        for (TradeProfitCount profitCount : calculateTradeProfitAsc) {
            if (i++ == 10) {
                break;
            }
            log.error(profitCount.getShareName() + "," + profitCount.getAmount());
        }
        i = 0;
        for (TradeProfitCount profitCount : calculateTradeProfitDesc) {
            if (i++ == 10) {
                break;
            }
            log.error(profitCount.getShareName() + "," + profitCount.getAmount());
        }
    }

    @Test
    public void testAddNewUser() throws Exception {
        User user = new User();
        user.setPassword("123456");
        user.setUserName("admin2");
        user.setBillId("13897822879");
        user.setEmail("richer@email.com");
        user.setZip("000000");
        user.setAddress("神州5号基地");
        userService.addNewUser(user);
        log.error(JSON.toJSONString(user));
    }

    @Test
    public void testFindUserByUserName() {
        log.error(userService.findByUserName("admin"));
    }

    @Test
    public void testValidateUser() {
        User user = new User();
        user.setUserName("admin");
        user.setPassword("123456");
        log.error(userService.validateUser(user));
        user.setUserName("user");
        log.error(userService.validateUser(user));
        user.setUserName("admin");
        user.setPassword("789000");
        log.error(userService.validateUser(user));
    }

    @Test
    public void testQryBalChange() throws Exception {
        List<BalanceChange> balanceChanges = balanceChangeService.findByUserIdAndUpdateTime(1, TimeUtil.getCurrentTimestamp(), TimeUtil.getDefaultExpireDate());
    }

    @Test
    public void testSaveBalance() throws Exception {
        Balance balance = new Balance();
        balance.setUpdateTime(TimeUtil.getCurrentTimestamp());
        balance.setCreateTime(TimeUtil.getCurrentTimestamp());
        balance.setDate(TimeUtil.getCurrentDay());
        balance.setUserId(1L);
        balance.setTotal(27239.84d);
        balance.setCashAmount(4330.04d);
        balance.setFundAmount(2011.8);
        balance.setShareAmount(20898.00);
        balance.setProfit(227.00);
        balance.setState(CommonConstant.State.STATE_VALID);
        balanceService.save(balance);
    }

    @Test
    public void testQryBalancePagination() throws Exception {
        Page<Balance> page = balanceService.findByUserIdPagination(1L, 0, 10, true);
        List<Balance> balances = page.getContent();
        for (Balance balance : balances) {
            log.error(balance);
        }
    }


    @Test
    public void testSaveBalanceFromFile() throws Exception {
        File file = new File("D:/profit.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line = null;
        List<Balance> balances = new ArrayList<>();
        while ((line = bufferedReader.readLine()) != null) {
            log.error(line);
            String[] data = line.split("\t");
            Balance balance = new Balance();
            balance.setState(CommonConstant.State.STATE_VALID);
            balance.setCashAmount(Double.parseDouble(data[4]));
            balance.setFundAmount(Double.parseDouble(data[3]));
            balance.setDate(data[0]);
            balance.setShareAmount(Double.parseDouble(data[2]));
            balance.setProfit(Double.parseDouble(data[1]));
            balance.setUserId(1L);
            balance.setTotal(balance.getCashAmount() + balance.getShareAmount() + balance.getFundAmount());
            balance.setCreateTime(TimeUtil.getTimestampFromString(balance.getDate(), TimeUtil.TimeFormat.YYYYMMDD));
            balance.setUpdateTime(TimeUtil.getCurrentTimestamp());
            balances.add(balance);
        }
        bufferedReader.close();
        balanceService.saveAll(balances);
    }

    @Test
    public void testUpdateRecord() throws Exception {
        TradeRecord tradeRecord = tradeRecordRepository.getOne(2436l);
        tradeRecord.setTradeSeq("1111");
        tradeRecordService.update(tradeRecord);
    }

    @Test
    public void testQryCurBalance() throws Exception {
        Balance balance = balanceService.findCurrentDayBalance(1L);
        Assert.assertNull(balance);
    }

    @Test
    public void testCalculateSomeProfit() throws Exception {
        profitService.calculateProfit(1l, "159915");
        profitService.calculateProfit(1L, "111");
    }
}
