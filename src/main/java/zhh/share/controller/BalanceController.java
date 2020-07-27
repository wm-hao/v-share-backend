package zhh.share.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import zhh.share.constant.CommonConstant;
import zhh.share.dto.BaseRequest;
import zhh.share.dto.BaseResponse;
import zhh.share.entity.Balance;
import zhh.share.service.BalanceService;
import zhh.share.util.CommonUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author richer
 * @date 2020/7/26 8:58 下午
 */
@RestController
@RequestMapping("/balance")
@CrossOrigin
public class BalanceController {

    private static transient Log log = LogFactory.getLog(BalanceController.class);

    @Autowired
    BalanceService balanceService;

    @PostMapping("/pagination")
    public BaseResponse getBalances(@RequestBody BaseRequest request) {
        try {
            Page<Balance> page = balanceService.findByUserIdPagination(request.getUserId(), request.getPage(), request.getSize(), StringUtils.equals(CommonConstant.Order.ASC, request.getTimeOrder()));
            BaseResponse baseResponse = CommonUtil.success(CommonConstant.Message.QRY_SUCCESS);
            List<Balance> balances = page.getContent();
            List<Balance> finalBalances = new ArrayList<>();
            finalBalances.addAll(balances);
            if (StringUtils.equals(CommonConstant.Reverse.YES, request.getReverse())) {
                Collections.reverse(finalBalances);
            }
            baseResponse.setTotal(page.getTotalElements());
            baseResponse.setRows(finalBalances);
            return baseResponse;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return CommonUtil.fail(e.getMessage());
        }
    }
}
