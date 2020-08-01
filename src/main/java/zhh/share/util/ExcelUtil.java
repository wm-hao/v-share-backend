package zhh.share.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import zhh.share.constant.CommonConstant;
import zhh.share.constant.ShareConstant;
import zhh.share.entity.TradeRecord;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author richer
 */
public class ExcelUtil {

    private static Log logger = LogFactory.getLog(ExcelUtil.class);

    public static final String XLS = "xls";
    public static final String XLSX = "xlsx";

    /**
     * 根据文件后缀名类型获取对应的工作簿对象
     *
     * @param inputStream 读取文件的输入流
     * @param fileType    文件后缀名类型（xls或xlsx）
     * @return 包含文件数据的工作簿对象
     */
    public static Workbook getWorkbook(InputStream inputStream, String fileType) throws IOException {
        Workbook workbook = null;
        if (fileType.equalsIgnoreCase(XLS)) {
            workbook = new HSSFWorkbook(inputStream);
        } else if (fileType.equalsIgnoreCase(XLSX)) {
            workbook = new XSSFWorkbook(inputStream);
        }
        return workbook;
    }

    /**
     * 读取Excel文件内容
     *
     * @param fileName 要读取的Excel文件所在路径
     * @return 读取结果列表，读取失败时返回null
     */
    public static List<TradeRecord> parseExcel2TradeRecord(String fileName) throws Exception {
        List<TradeRecord> resultDataList = new ArrayList<>();
        Workbook workbook = null;
        FileInputStream inputStream = null;

        try {
            // 获取Excel后缀名
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
            // 获取Excel文件
            File excelFile = new File(fileName);
            if (!excelFile.exists()) {
                logger.warn("指定的Excel文件不存在！");
                return null;
            }

            // 获取Excel工作簿
            inputStream = new FileInputStream(excelFile);
            workbook = getWorkbook(inputStream, fileType);

            // 读取excel中的数据
            resultDataList = doParseExcel2TradeRecord(workbook);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            try {
                if (null != workbook) {
                    workbook.close();
                }
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (Exception e) {
                String errMsg = "关闭数据流出错！错误信息：" + e.getMessage();
                logger.error(errMsg);
            }
        }
        return resultDataList;
    }

    /**
     * 解析Excel数据
     *
     * @param workbook Excel工作簿对象
     * @return 解析结果
     */
    private static List<TradeRecord> doParseExcel2TradeRecord(Workbook workbook) throws Exception {
        List<TradeRecord> resultDataList = new ArrayList<>();
        // 解析sheet
        for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
            Sheet sheet = workbook.getSheetAt(sheetNum);

            // 校验sheet是否合法
            if (sheet == null) {
                continue;
            }

            // 获取第一行数据
            int firstRowNum = sheet.getFirstRowNum();
            logger.error("第一行:" + firstRowNum);
            Row firstRow = sheet.getRow(firstRowNum);
            if (null == firstRow) {
                logger.warn("解析Excel失败，在第一行没有读取到任何数据！");
            }

            int rowEnd = sheet.getPhysicalNumberOfRows();
            for (int rowNum = firstRowNum; rowNum < rowEnd; rowNum++) {
                Row row = sheet.getRow(rowNum);

                if (null == row) {
                    continue;
                }

                resultDataList.add(convertRowToData(row));
            }
        }

        return resultDataList;
    }

    /**
     * 将单元格内容转换为字符串
     */
    public static String convertCellValueToString(Cell cell) {
        if (cell == null) {
            return null;
        }
        String returnValue = null;
        switch (cell.getCellType()) {
            case NUMERIC:
                returnValue = new DecimalFormat("#.######").format(cell.getNumericCellValue());
                break;
            case STRING:
                returnValue = cell.getStringCellValue();
                break;
            case BOOLEAN:
                boolean booleanValue = cell.getBooleanCellValue();
                returnValue = String.valueOf(booleanValue);
                break;
            case BLANK:
                break;
            case FORMULA:
                returnValue = cell.getCellFormula();
                break;
            case ERROR:
                break;
            default:
                break;
        }
        if (returnValue != null) {
            return returnValue.trim().replaceAll("\\t", "");
        }
        return null;
    }

    /**
     * 提取每一行中需要的数据，构造成为一个结果数据对象
     * 当该行中有单元格的数据为空或不合法时，忽略该行的数据
     */
    private static TradeRecord convertRowToData(Row row) throws Exception {
        TradeRecord resultData = new TradeRecord();

        Cell cell;
        int cellNum = 0;
        cell = row.getCell(cellNum++);
        // 第一列 日期
        String date = convertCellValueToString(cell);
        // 第二列 时间
        cell = row.getCell(cellNum++);
        String time = convertCellValueToString(cell);
        Date tradeTime = TimeUtil.getTimestampFromString(date + time, TimeUtil.TimeFormat.YYYYMMDDHI_MM_SS);
        // 第三列 名称
        cell = row.getCell(cellNum++);
        String name = convertCellValueToString(cell);
        // 第四列 代码
        cell = row.getCell(cellNum++);
        String code = convertCellValueToString(cell);
        // 第五列 操作类型
        cell = row.getCell(cellNum++);
        String optType = convertCellValueToString(cell);
        // 第六列 市场所属地
        cell = row.getCell(cellNum++);
        String stockExchange = convertCellValueToString(cell);
        // 第七列 交易数量
        cell = row.getCell(cellNum++);
        String count = convertCellValueToString(cell);
        // 第八列 单价
        cell = row.getCell(cellNum++);
        String unitPrice = convertCellValueToString(cell);
        // 第九列 交易金额
        cell = row.getCell(cellNum++);
        String amount = convertCellValueToString(cell);
        // 第十列 流水
        cell = row.getCell(cellNum);
        String tradeSeq = convertCellValueToString(cell);
        resultData.setShareName(name);
        resultData.setShareCode(code);
        resultData.setFee(5.00);
        if (stockExchange.contains(ShareConstant.StockExchange.ShenZhen.getDesc())) {
            resultData.setStockExchange(ShareConstant.StockExchange.ShenZhen.getType());
        } else if (stockExchange.contains(ShareConstant.StockExchange.ShangHai.getDesc())) {
            resultData.setStockExchange(ShareConstant.StockExchange.ShangHai.getType());
        }
        if (optType.contains(ShareConstant.PayType.BUY.getDesc())) {
            resultData.setPayType(ShareConstant.PayType.BUY.getType());
        } else if (optType.contains(ShareConstant.PayType.SELL.getDesc())) {
            resultData.setPayType(ShareConstant.PayType.SELL.getType());
        } else if (optType.contains(ShareConstant.PayType.BONUS.getDesc())) {
            resultData.setPayType(ShareConstant.PayType.BONUS.getType());
        } else if (optType.contains(ShareConstant.PayType.TAX.getDesc())) {
            resultData.setPayType(ShareConstant.PayType.TAX.getType());
        }
        resultData.setPayTime(tradeTime);
        resultData.setDate(TimeUtil.getStringFromTimestamp(tradeTime, TimeUtil.TimeFormat.YYYY_MM_DD));
        resultData.setCreateTime(TimeUtil.getCurrentTimestamp());
        resultData.setUpdateTime(TimeUtil.getCurrentTimestamp());
        resultData.setPayAmount(Double.parseDouble(amount));
        resultData.setPayCount(Math.abs(Double.parseDouble(count)));
        resultData.setUnitPrice(Double.parseDouble(unitPrice));
        resultData.setAlias(PinyinUtil.getPinYinHeadChar(name));
        resultData.setState(CommonConstant.State.STATE_VALID);
        resultData.setTradeSeq(tradeSeq);
        return resultData;
    }


}
