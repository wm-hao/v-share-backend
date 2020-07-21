package zhh.share.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author richer
 * @date 2020/7/21 4:07 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse implements Serializable {
    private String code;
    private String message;
    private String desc;
    private long total;
    private List rows = new ArrayList();

    public BaseResponse(String code, String message, String desc) {
        this.code = code;
        this.message = message;
        this.desc = desc;
    }
}
