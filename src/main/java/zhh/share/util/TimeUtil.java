package zhh.share.util;

import org.apache.commons.lang3.StringUtils;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * @author richer
 * @date 2020/7/21 11:13 上午
 */
public class TimeUtil {

    public enum TimeFormat {
        YYYYMMDDHIMMSS("yyyyMMddHHmmss"),
        YYYYMMDDHI_MM_SS("yyyyMMddHH:mm:ss"),
        YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss"),
        YYYYMMDD("yyyyMMdd");

        private TimeFormat(String format) {
            this.format = format;
        }

        private String format;

        public String getFormat() {
            return format;
        }
    }


    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static Timestamp getTimestampFromString(String dateTime, TimeFormat timeFormat) throws Exception {
        if (StringUtils.isNotBlank(dateTime)) {
            return new Timestamp(new SimpleDateFormat(timeFormat.getFormat()).parse(dateTime).getTime());
        }
        return null;
    }

    public static String getStringFromTimestamp(Date date, TimeFormat timeFormat) throws Exception {
        if (date != null) {
            return new SimpleDateFormat(timeFormat.getFormat()).format(date);
        }
        return null;
    }

    public static Timestamp getDefaultExpireDate() throws Exception {
        return getTimestampFromString("2121-12-21 12:21:22", TimeFormat.YYYY_MM_DD_HH_MM_SS);
    }

    public static String getCurrentDay() throws Exception {
        return getStringFromTimestamp(new Date(System.currentTimeMillis()), TimeFormat.YYYYMMDD);
    }
}
