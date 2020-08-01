package zhh.share.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import zhh.share.constant.CommonConstant;
import zhh.share.dto.BaseResponse;
import zhh.share.entity.Journal;
import zhh.share.service.JournalService;
import zhh.share.util.CommonUtil;

/**
 * @author richer
 * @date 2020/8/1 1:07 下午
 */
@RequestMapping("/journal")
@RestController
@CrossOrigin
public class JournalController {

    @Autowired
    JournalService journalService;

    @GetMapping("/pagination")
    public BaseResponse pagination(@RequestParam long userId, @RequestParam int page, @RequestParam int size) throws Exception{
        BaseResponse response = CommonUtil.success(CommonConstant.Message.QRY_SUCCESS);
        Page<Journal> journalPage = journalService.findByUserId(userId, page, size);
        response.setTotal(journalPage.getTotalElements());
        response.setRows(journalPage.getContent());
        return response;
    }

    @PostMapping("/add")
    public BaseResponse add(@RequestBody Journal journal) throws Exception{
        journalService.addNewJournal(journal);
        return CommonUtil.success(CommonConstant.Message.ADD_SUCCESS);
    }

    @PostMapping("/update")
    public BaseResponse update(@RequestBody Journal journal) throws Exception{
        journalService.updateJournal(journal);
        return CommonUtil.success(CommonConstant.Message.UPDATE_SUCCESS);
    }
}
