package zhh.share.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author richer
 * @date 2020/7/30 2:21 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Pagination {
    private int page;
    private int size;
    private int start;
    private int end;
}
