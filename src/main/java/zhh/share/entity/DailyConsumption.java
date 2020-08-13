package zhh.share.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author richer
 * @date 2020/8/13 5:07 下午
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@ToString(callSuper = true)
public class DailyConsumption extends BaseEntity {

    @Column(columnDefinition = "double comment '支出'", nullable = false)
    private Double amount;
    @Column(columnDefinition = "text comment '备注'", nullable = false)
    private String remarks;
}
