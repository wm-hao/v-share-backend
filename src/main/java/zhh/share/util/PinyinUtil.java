package zhh.share.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PinyinUtil {

    private static transient Log log = LogFactory.getLog(PinyinUtil.class);

    public static String getPingYin(String src) throws Exception {
        if (StringUtils.isNotBlank(src)) {
            char[] inputChar = src.toCharArray();
            String[] pinyinArray;
            HanyuPinyinOutputFormat hPinyinOutputFormat = new HanyuPinyinOutputFormat();
            hPinyinOutputFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
            //带拼音状态(toneType和charType必须成对使用，否则会异常)
             /* hPinyinOutputFormat.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);
              hPinyinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);*/
            hPinyinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            hPinyinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
            StringBuilder str = new StringBuilder();
            try {
                for (char v : inputChar) {
                    // 判断是否为汉字字符
                    if (Character.toString(v).matches("[\\u4E00-\\u9FA5]+")) {
                        pinyinArray = PinyinHelper.toHanyuPinyinStringArray(v, hPinyinOutputFormat);
                        str.append(pinyinArray[0]);
                    } else {
                        str.append(v);
                    }
                }
                return str.toString();
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                log.error("获取汉字对应的拼音失败:");
                log.error(e.getMessage(), e);
                throw e;
            }
        }
        return null;
    }

    // 返回中文的首字母
    public static String getPinYinHeadChar(String str) throws BadHanyuPinyinOutputFormatCombination {
        if (StringUtils.isNotBlank(str)) {
            StringBuilder convert = new StringBuilder();
            HanyuPinyinOutputFormat hPinyinOutputFormat = new HanyuPinyinOutputFormat();
            hPinyinOutputFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
            for (int j = 0; j < str.length(); j++) {
                char word = str.charAt(j);
                String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word, hPinyinOutputFormat);
                if (pinyinArray != null) {
                    convert.append(pinyinArray[0].charAt(0));
                } else {
                    convert.append(word);
                }
            }
            return convert.toString();
        }
        return null;
    }

    // 将字符串转移为ASCII码
    public static String getCnASCII(String cnStr) {
        if (StringUtils.isNotBlank(cnStr)) {
            StringBuilder stringBuffer = new StringBuilder();
            for (byte b : cnStr.getBytes()) {
                stringBuffer.append(Integer.toHexString(b & 0xff));
            }
            return stringBuffer.toString();
        }
        return null;
    }

}
