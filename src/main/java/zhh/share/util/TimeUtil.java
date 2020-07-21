package zhh.share.util;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author richer
 * @date 2020/7/21 11:13 上午
 */
public class TimeUtil {

    public enum TimeFormat {
        YYYYMMDDHIMMSS("yyyyMMddHHmmss"),
        YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss");

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

    public static Timestamp getTimestampFromString(String dateTime, TimeFormat timeFormat) throws ParseException {
        if (StringUtils.isNotBlank(dateTime)) {
            return new Timestamp(new SimpleDateFormat(timeFormat.getFormat()).parse(dateTime).getTime());
        }
        return null;
    }
}
