package zhh.share.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import zhh.share.constant.CommonConstant;
import zhh.share.dto.BaseResponse;
import zhh.share.entity.DailyConsumption;
import zhh.share.pojo.Statistic;
import zhh.share.service.DailyConsumptionService;
import zhh.share.util.CommonUtil;

import java.util.List;

/**
 * @author richer
 * @date 2020/8/13 5:18 下午
 */
@RestController
@CrossOrigin
@RequestMapping("/consumption")
public class DailyController {

    @Autowired
    DailyConsumptionService dailyConsumptionService;

    @GetMapping("/pagination")
    public BaseResponse pagination(@RequestParam long userId, @RequestParam int page, @RequestParam int size) throws Exception {
        BaseResponse response = CommonUtil.success(CommonConstant.Message.QRY_SUCCESS);
        Page<DailyConsumption> pages = dailyConsumptionService.findByUserId(userId, page, size, false);
        response.setRows(pages.getContent());
        response.setTotal(pages.getTotalElements());
        return response;
    }

    @PostMapping("/add")
    public BaseResponse add(@RequestBody DailyConsumption dailyConsumption) throws Exception {
        BaseResponse response = CommonUtil.success(CommonConstant.Message.ADD_SUCCESS);
        dailyConsumptionService.addNew(dailyConsumption);
        return response;
    }

    @GetMapping("/group")
    public BaseResponse group(@RequestParam long userId) throws Exception {
        BaseResponse response = CommonUtil.success(CommonConstant.Message.QRY_SUCCESS);
        List<Statistic> statistics = dailyConsumptionService.groupByDate(userId);
        response.setRows(statistics);
        return response;
    }
}
