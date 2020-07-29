package zhh.share.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zhh.share.constant.CommonConstant;
import zhh.share.dto.BaseResponse;
import zhh.share.pojo.TradeProfitCount;
import zhh.share.service.ProfitService;

/**
 * @author richer
 * @date 2020/7/29 5:41 下午
 */
@CrossOrigin
@RestController("/profit")
public class ProfitController {


    @Autowired
    ProfitService profitService;

    @GetMapping("/top")
    @ResponseBody
    public BaseResponse top(@RequestParam String type) throws Exception {
        List<TradeProfitCount>
        if (StringUtils.equals(CommonConstant.Top.LOSS, type)) {

        }

    }


}
