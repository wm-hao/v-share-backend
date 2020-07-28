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
import zhh.share.util.TimeUtil;

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

    @PostMapping("/today")
    public BaseResponse updateCurrentDayBalance(@RequestBody Balance balance) {
        List<Balance> balances = new ArrayList<>();
        try {
            Balance qryBalance = balanceService.findCurrentDayBalance(balance.getUserId());
            if (qryBalance == null) {
                qryBalance = balance;
                qryBalance.setUpdateTime(TimeUtil.getCurrentTimestamp());
                qryBalance.setCreateTime(TimeUtil.getCurrentTimestamp());
                qryBalance.setState(CommonConstant.State.STATE_VALID);
                qryBalance.setDate(TimeUtil.getCurrentDay());
                qryBalance.setTotal(CommonUtil.processDoubleNull(balance.getCashAmount()) + CommonUtil.processDoubleNull(balance.getFundAmount()) + CommonUtil.processDoubleNull(balance.getShareAmount()));
                balanceService.save(qryBalance);
            } else {
                qryBalance.setUpdateTime(TimeUtil.getCurrentTimestamp());
                qryBalance.setProfit(balance.getProfit());
                qryBalance.setFundAmount(balance.getFundAmount());
                qryBalance.setCashAmount(balance.getCashAmount());
                qryBalance.setShareAmount(balance.getShareAmount());
                qryBalance.setTotal(CommonUtil.processDoubleNull(balance.getCashAmount()) + CommonUtil.processDoubleNull(balance.getFundAmount()) + CommonUtil.processDoubleNull(balance.getShareAmount()));
                balanceService.save(qryBalance);
            }
            balances.add(qryBalance);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return CommonUtil.fail(e.getMessage());
        }
        BaseResponse response = CommonUtil.success(CommonConstant.Message.ADD_CUR_DAY_BALANCE);
        response.setRows(balances);
        response.setTotal(balances.size());
        return response;
    }

    @GetMapping("/today")
    public BaseResponse qryCurrentDayBalance(@RequestParam long userId) {
        List<Balance> balances = new ArrayList<>();
        try {
            Balance qryBalance = balanceService.findCurrentDayBalance(userId);
            if (qryBalance == null) {
                qryBalance = new Balance();
                qryBalance.setProfit(0d);
                qryBalance.setTotal(0d);
                qryBalance.setShareAmount(0d);
                qryBalance.setCashAmount(0d);
                qryBalance.setFundAmount(0d);
            }
            balances.add(qryBalance);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return CommonUtil.fail(e.getMessage());
        }
        BaseResponse response = CommonUtil.success(CommonConstant.Message.QRY_CUR_DAY_BALANCE);
        response.setRows(balances);
        response.setTotal(balances.size());
        return response;
    }
}
