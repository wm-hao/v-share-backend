package zhh.share.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zhh.share.constant.CommonConstant;
import zhh.share.dto.BaseResponse;
import zhh.share.entity.Chance;
import zhh.share.service.ChanceService;
import zhh.share.util.CommonUtil;
import zhh.share.util.TimeUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author richer
 * @date 2020/8/19 4:29 下午
 */
@Controller
@CrossOrigin
@RequestMapping("/chance")
public class ChanceController {

    private static transient Log log = LogFactory.getLog(ChanceController.class);

    private static final String DELIMETER = "\t";
    private static final String FILE_NAME_DEF = "计划池";
    private static final String FILE_SUFFIX = ".txt";
    private static final String FILE_HEADLINE = "股票名称\t股票代码\t热度\t选入时价格\t当前价格\t备注\t板块\\概念";

    @Autowired
    ChanceService chanceService;

    @Value("${upload.dir}")
    private String dir;

    @GetMapping("/all")
    @ResponseBody
    public BaseResponse findAll(@RequestParam long userId) throws Exception {
        List<Chance> chances = chanceService.findAll(userId);
        return CommonUtil.success(CommonConstant.Message.QRY_SUCCESS, chances);
    }

    @PostMapping("/add")
    @ResponseBody
    public BaseResponse addNewChance(@RequestBody Chance chance) throws Exception {
        chanceService.add(chance);
        return CommonUtil.success(CommonConstant.Message.ADD_SUCCESS);
    }

    @PostMapping("/update")
    @ResponseBody
    public BaseResponse updateChance(@RequestBody Chance chance) throws Exception {
        chanceService.update(chance);
        return CommonUtil.success(CommonConstant.Message.UPDATE_SUCCESS);
    }

    @PostMapping("/import")
    @ResponseBody
    public BaseResponse importByTxt(@RequestParam("file") MultipartFile file, @RequestParam("userId") long userId) throws Exception {
        log.error("文件上传出入开始:");
        if (file.isEmpty()) {
            return CommonUtil.fail("上传文件为空");
        }
        String fileName = file.getOriginalFilename();
        String filePath = dir + File.separator;
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            List<Chance> chances = parseFile2Chances(dest);
            if (!chances.isEmpty()) {
                for (Chance chance : chances) {
                    chance.setUserId(userId);
                }
            }
            chanceService.saveAll(chances);
            log.info("通过文件导入机会记录成功");
        } catch (Exception e) {
            log.error("通过文件导入机会记录失败:");
            log.error(e.getMessage(), e);
            return CommonUtil.fail(e.getMessage());
        }
        return CommonUtil.success(CommonConstant.Message.IMPORT_SUCCESS);
    }

    @GetMapping("/export")
    public Object downloadFile(HttpServletResponse response, @RequestParam String createTime, @RequestParam long userId) throws Exception {
        String fileName = FILE_NAME_DEF + System.currentTimeMillis() + FILE_SUFFIX;
        String fullFileName = dir + File.separator + fileName;
        generateFile(fullFileName, userId, createTime);
        OutputStream os = null;
        InputStream is = null;
        try {
            // 取得输出流
            os = response.getOutputStream();
            // 清空输出流
            response.reset();
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setContentType("application/x-download;charset=GBK");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"), "iso-8859-1"));
            //读取流
            File f = new File(fullFileName);
            is = new FileInputStream(f);
            //复制
            IOUtils.copy(is, response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException e) {
            return CommonUtil.fail("下载附件失败,error:" + e.getMessage());
        }
        //文件的关闭放在finally中
        finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        return null;
    }


    private void generateFile(String fileName, long userId, String time) throws Exception {
        Timestamp createTime = TimeUtil.getTimestampFromString(TimeUtil.getCurrentDay(), TimeUtil.TimeFormat.YYYY_MM_DD);
        if (StringUtils.isNotBlank(time)) {
            createTime = TimeUtil.getTimestampFromString(time, TimeUtil.TimeFormat.YYYYMMDD);
        }
        List<Chance> chances = chanceService.findAllAfterCreateTime(userId, createTime);
        File dest = new File(fileName);
        if (!dest.exists()) {
            dest.createNewFile();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dest)));
        bufferedWriter.write(FILE_HEADLINE);
        if (chances != null && !chances.isEmpty()) {
            for (Chance chance : chances) {
                bufferedWriter.newLine();
                String row = chance.getShareName() + DELIMETER +
                        chance.getShareCode() + DELIMETER +
                        chance.getHot() + DELIMETER +
                        chance.getInitPrice() + DELIMETER +
                        chance.getCurrentPrice() + DELIMETER +
                        chance.getRemarks() + DELIMETER +
                        chance.getTags();
                bufferedWriter.write(row);
            }
        }
        bufferedWriter.close();
    }

    private static List<Chance> parseFile2Chances(File file) throws Exception {
        List<Chance> chances = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String row = null;
        int rowNumber = 0;
        while ((row = bufferedReader.readLine()) != null) {
            if (rowNumber > 0 && StringUtils.isNotBlank(row)) {
                String[] columns = row.split(DELIMETER);
                Chance chance = new Chance();
                chance.setShareName(columns[0]);
                chance.setShareCode(columns[1]);
                chance.setHot(StringUtils.isBlank(columns[2]) ? 0 : Integer.parseInt(columns[2]));
                chance.setInitPrice(StringUtils.isBlank(columns[3]) ? 0d : Double.parseDouble(columns[3]));
                chance.setCurrentPrice(StringUtils.isBlank(columns[4]) ? 0d : Double.parseDouble(columns[4]));
                chance.setRemarks(columns[5]);
                chance.setTags(columns[6]);
                chances.add(chance);
            }
            rowNumber++;
        }
        bufferedReader.close();
        return chances;
    }
}
