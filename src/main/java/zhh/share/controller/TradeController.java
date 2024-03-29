package zhh.share.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zhh.share.constant.CommonConstant;
import zhh.share.constant.ShareConstant;
import zhh.share.dto.BaseRequest;
import zhh.share.dto.BaseResponse;
import zhh.share.dto.Pagination;
import zhh.share.entity.TradeRecord;
import zhh.share.pojo.TradeRecordCount;
import zhh.share.pojo.TradeRecordCountBean;
import zhh.share.service.ProfitService;
import zhh.share.service.TradeRecordService;
import zhh.share.util.CommonUtil;
import zhh.share.util.ExcelUtil;
import zhh.share.util.TimeUtil;

import java.io.File;
import java.util.*;

/**
 * @author richer
 * @date 2020/7/21 12:51 下午
 */
@RequestMapping("/trade")
@RestController
@CrossOrigin
public class TradeController {

    private static transient Log log = LogFactory.getLog(TradeController.class);

    @Autowired
    TradeRecordService tradeRecordService;

    @Autowired
    ProfitService profitService;

    @Value("${upload.dir}")
    private String dir;

    @GetMapping("/list/page")
    public List<TradeRecord> findByPage(@RequestParam int page, @RequestParam int size) {
        Page<TradeRecord> tradeRecords = tradeRecordService.findByPage(page, size);
        return tradeRecords.getContent();
    }

    @RequestMapping(value = "/pagination", method = {RequestMethod.POST, RequestMethod.OPTIONS})
    public BaseResponse findByPageAllProps(@RequestBody BaseRequest baseRequest) {
        CommonUtil.convertPagination(baseRequest);
        log.error(baseRequest);
        try {
            Page<TradeRecord> tradeRecords = tradeRecordService.findByAllProps(baseRequest.getUserId(), baseRequest.getPage(), baseRequest.getSize(), baseRequest.getShareName(),
                    baseRequest.getShareCode(), baseRequest.getAlias(), baseRequest.getPayType(), baseRequest.getStartTime(), baseRequest.getEndTime(), StringUtils.equals(CommonConstant.Order.ASC, baseRequest.getTimeOrder()));
            BaseResponse response = CommonUtil.success(CommonConstant.Message.QRY_SUCCESS);
            response.setTotal(tradeRecords.getTotalElements());
            response.setRows(tradeRecords.getContent());
            return response;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return CommonUtil.fail(e.getMessage());
        }
    }

    @PostMapping("import")
    @ResponseBody
    public BaseResponse importByXls(@RequestParam("file") MultipartFile file, @RequestParam("userId") long userId) {
        log.error("文件上传出入开始:");
        if (file.isEmpty()) {
            return CommonUtil.fail("上传文件为空");
        }
        String fileName = file.getOriginalFilename();
        String filePath = dir + File.separator;
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            List<TradeRecord> tradeRecords = ExcelUtil.parseExcel2TradeRecord(dest.getAbsolutePath());
            Set<String> shareCodes = new HashSet<>();
            for (TradeRecord tradeRecord : tradeRecords) {
                log.error(tradeRecord);
                tradeRecord.setUserId(userId);
                shareCodes.add(tradeRecord.getShareCode());
            }
            tradeRecordService.saveAll(tradeRecords);
            for (String shareCode : shareCodes) {
                profitService.calculateProfit(userId, shareCode);
            }
            log.info("上传成功");
        } catch (Exception e) {
            log.error("通过表格插入交易记录失败:");
            log.error(e.getMessage(), e);
            return CommonUtil.fail(e.getMessage());
        }
        return CommonUtil.success(CommonConstant.Message.IMPORT_TRADE_RECORD);
    }

    @PostMapping("/add")
    public BaseResponse addNewTradeRecord(@RequestBody TradeRecord tradeRecord) {
        if (tradeRecord != null) {
            try {
                tradeRecord.setCreateTime(TimeUtil.getCurrentTimestamp());
                tradeRecord.setDate(TimeUtil.getStringFromTimestamp(tradeRecord.getPayTime(), TimeUtil.TimeFormat.YYYY_MM_DD));
                tradeRecord.setUpdateTime(TimeUtil.getCurrentTimestamp());
                tradeRecord.setState(CommonConstant.State.STATE_VALID);
                ShareConstant.StockExchange stockExchange = CommonUtil.convertStockExchange(tradeRecord.getShareCode());
                tradeRecord.setStockExchange(stockExchange == null ? "" : stockExchange.getType());
                tradeRecordService.save(tradeRecord);
                profitService.calculateProfit(tradeRecord.getUserId(), tradeRecord.getShareCode());
            } catch (Exception e) {
                log.error(e.getMessage());
                return CommonUtil.fail(e.getMessage());
            }

        } else {
            return CommonUtil.fail(CommonConstant.Message.INFO_EMPTY);
        }
        return CommonUtil.success(CommonConstant.Message.ADD_NEW_RECORD);
    }

    @PostMapping("/update")
    public BaseResponse updateTradeRecord(@RequestBody TradeRecord tradeRecord) {
        if (tradeRecord != null) {
            try {
                tradeRecordService.update(tradeRecord);
            } catch (Exception e) {
                log.error(e.getMessage());
                return CommonUtil.fail(e.getMessage());
            }

        } else {
            return CommonUtil.fail(CommonConstant.Message.INFO_EMPTY);
        }
        return CommonUtil.success(CommonConstant.Message.UPDATE_RECORD);
    }

    @PostMapping("/delete")
    public BaseResponse deleteTradeRecord(@RequestBody TradeRecord tradeRecord) {
        if (tradeRecord != null) {
            try {
                tradeRecord.setState(CommonConstant.State.STATE_INVALID);
                tradeRecordService.update(tradeRecord);
            } catch (Exception e) {
                log.error(e.getMessage());
                return CommonUtil.fail(e.getMessage());
            }

        } else {
            return CommonUtil.fail(CommonConstant.Message.INFO_EMPTY);
        }
        return CommonUtil.success(CommonConstant.Message.DELETE_RECORD);
    }

    @GetMapping("/frequency")
    public BaseResponse frequency(@RequestParam long userId, @RequestParam String type) {
        List<TradeRecordCountBean> finalRecordCounts = new ArrayList<>();
        try {
            List<TradeRecordCount> tradeRecordCounts = tradeRecordService.frequency(userId);
            Map<String, Long> counts = new TreeMap<>();
            if (StringUtils.equals(CommonConstant.FrequencyType.YEAR, type)) {
                for (TradeRecordCount count : tradeRecordCounts) {
                    String date = count.getDate();
                    if (StringUtils.isNotBlank(date)) {
                        date = date.substring(0, 4);
                    }
                    if (!counts.containsKey(date)) {
                        counts.put(date, 0L);
                    }
                    counts.put(date, count.getTotal() + counts.get(date));
                }
            } else if (StringUtils.equals(CommonConstant.FrequencyType.MONTH, type)) {
                for (TradeRecordCount count : tradeRecordCounts) {
                    String date = count.getDate();
                    if (StringUtils.isNotBlank(date)) {
                        date = date.substring(0, 7);
                    }
                    if (!counts.containsKey(date)) {
                        counts.put(date, 0L);
                    }
                    counts.put(date, count.getTotal() + counts.get(date));
                }
            } else {
                for (TradeRecordCount count : tradeRecordCounts) {
                    String date = count.getDate();
                    if (!counts.containsKey(date)) {
                        counts.put(date, 0L);
                    }
                    counts.put(date, count.getTotal() + counts.get(date));
                }
            }
            for (String date : counts.keySet()) {
                TradeRecordCountBean bean = new TradeRecordCountBean();
                bean.setDate(date);
                bean.setTotal(counts.get(date));
                finalRecordCounts.add(bean);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return CommonUtil.fail(e.getMessage());
        }
        BaseResponse response = CommonUtil.success(CommonConstant.Message.QRY_SUCCESS);
        response.setTotal(finalRecordCounts.size());
        response.setRows(finalRecordCounts);
        return response;
    }

    @GetMapping("/top")
    @ResponseBody
    public BaseResponse top(@RequestParam long userId, @RequestParam int page, @RequestParam int size) throws Exception {
        BaseResponse response = CommonUtil.success(CommonConstant.Message.QRY_SUCCESS);
        List<TradeRecordCount> recordCounts = tradeRecordService.top(userId);
        Pagination pagination = new Pagination();
        pagination.setPage(page);
        pagination.setSize(size);
        CommonUtil.processPagination(pagination, recordCounts.size());
        response.setRows(recordCounts.subList(pagination.getStart(), pagination.getEnd()));
        response.setTotal(recordCounts.size());
        return response;
    }
}
