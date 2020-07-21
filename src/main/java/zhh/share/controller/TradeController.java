package zhh.share.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import zhh.share.constant.CommonConstant;
import zhh.share.dto.BaseRequest;
import zhh.share.dto.BaseResponse;
import zhh.share.entity.TradeRecord;
import zhh.share.service.TradeRecordService;
import zhh.share.util.CommonUtil;

import java.util.List;

/**
 * @author richer
 * @date 2020/7/21 12:51 下午
 */
@RequestMapping("/trade")
@RestController
public class TradeController {

    private static transient Log log = LogFactory.getLog(TradeController.class);

    @Autowired
    TradeRecordService tradeRecordService;

    @GetMapping("/list/page")
    public List<TradeRecord> findByPage(@RequestParam int page, @RequestParam int size) {
        Page<TradeRecord> tradeRecords = tradeRecordService.findByPage(page, size);
        return tradeRecords.getContent();
    }

    @PostMapping("/list")
    public BaseResponse findByPageAllProps(@RequestBody BaseRequest baseRequest) {
        CommonUtil.convertPagination(baseRequest);
        log.error(baseRequest);
        try {
            Page<TradeRecord> tradeRecords = tradeRecordService.findByAllProps(baseRequest.getPage(), baseRequest.getSize(), baseRequest.getShareName(),
                    baseRequest.getShareCode(), baseRequest.getPayType(), baseRequest.getStartTime(), baseRequest.getEndTime());
            BaseResponse response = CommonUtil.success(CommonConstant.Message.QRY_SUCCESS);
            response.setTotal(tradeRecords.getTotalElements());
            response.setRows(tradeRecords.getContent());
            return response;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return CommonUtil.fail(e.getMessage());
        }
    }

}
