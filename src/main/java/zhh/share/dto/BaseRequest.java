package zhh.share.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author richer
 * @date 2020/7/21 4:05 下午
 */
@Data
public class BaseRequest {
    private int page;
    private int size;
    private String shareName;
    private String shareCode;
    private String payType;
    private String timeOrder;
    private long userId;
    private String reverse;
    private String alias;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp endTime;
}
