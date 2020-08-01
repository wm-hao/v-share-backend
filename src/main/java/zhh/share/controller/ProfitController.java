package zhh.share.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import zhh.share.constant.CommonConstant;
import zhh.share.dto.BaseResponse;
import zhh.share.entity.Profit;
import zhh.share.pojo.TradeProfitCount;
import zhh.share.service.ProfitService;
import zhh.share.util.CommonUtil;

import java.util.List;

/**
 * @author richer
 * @date 2020/7/29 5:41 下午
 */
@CrossOrigin
@RequestMapping("/profit")
@RestController
public class ProfitController {


    @Autowired
    ProfitService profitService;

    @GetMapping("/top")
    @ResponseBody
    public BaseResponse top(@RequestParam String type, @RequestParam long userId, @RequestParam int page, @RequestParam int size) throws Exception {
        List<TradeProfitCount> tradeProfitCounts = null;
        if (StringUtils.equals(CommonConstant.Top.LOSS, type)) {
            tradeProfitCounts = profitService.calculateTradeLoss(userId);
        } else if (StringUtils.equals(CommonConstant.Top.PROFIT, type)) {
            tradeProfitCounts = profitService.calculateTradeProfit(userId);
        } else {
            throw new Exception("查询类型不正确");
        }
        page = page < 0 ? 0 : page;
        size = size < 0 ? 1 : size;
        int start = page * size;
        int end = Math.min(start + size, tradeProfitCounts.size());
        BaseResponse baseResponse = CommonUtil.success(CommonConstant.Message.QRY_SUCCESS);
        baseResponse.setTotal(tradeProfitCounts.size());
        baseResponse.setRows(tradeProfitCounts.subList(start, end));
        return baseResponse;
    }

    @GetMapping("/compare")
    @ResponseBody
    public BaseResponse compare(@RequestParam long userId) throws Exception {
        BaseResponse response = CommonUtil.success(CommonConstant.Message.QRY_SUCCESS);
        List<TradeProfitCount> tradeProfitCounts = profitService.profitLossCompare(userId);
        response.setTotal(tradeProfitCounts.size());
        response.setRows(tradeProfitCounts);
        return response;
    }

    @GetMapping("/total")
    @ResponseBody
    public BaseResponse total(@RequestParam long userId) throws Exception {
        BaseResponse response = CommonUtil.success(CommonConstant.Message.QRY_SUCCESS);
        List<TradeProfitCount> tradeProfitCounts = profitService.profit(userId);
        response.setTotal(tradeProfitCounts.size());
        response.setRows(tradeProfitCounts);
        return response;
    }

    @GetMapping("/pagination")
    @ResponseBody
    public BaseResponse pagination(@RequestParam long userId, @RequestParam int page, @RequestParam int size, @RequestParam String order, @RequestParam String shareName, @RequestParam String showKeep) throws Exception {
        BaseResponse response = CommonUtil.success(CommonConstant.Message.QRY_SUCCESS);
        Page<Profit> profits = profitService.pagination(userId, page, size, StringUtils.equals(CommonConstant.Order.ASC, order), shareName, showKeep);
        response.setTotal(profits.getTotalElements());
        for (Profit profit : profits.getContent()) {
            if (profit != null) {
                profit.setKeepCount(CommonUtil.processDoubleNull(profit.getKeepCount()));
                profit.setKeepAmount(CommonUtil.processDoubleNull(profit.getKeepAmount()));
                profit.setProfit(CommonUtil.processDoubleNull(profit.getProfit()));
            }
        }
        response.setRows(profits.getContent());
        return response;
    }

}
