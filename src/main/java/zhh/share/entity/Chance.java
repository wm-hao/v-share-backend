package zhh.share.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author richer
 * @date 2020/8/19 4:04 下午
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@Entity
public class Chance extends BaseEntity {

    @Column(columnDefinition = "varchar(100) comment '股票名称'", nullable = false)
    private String shareName;
    @Column(columnDefinition = "varchar(50) comment '股票代码'", nullable = false)
    private String shareCode;
    @Column(columnDefinition = "int(3) comment'值得买概率'")
    private Integer hot;
    @Column(columnDefinition = "text comment'理由'")
    private String remarks;
    @Column(columnDefinition = "double comment'选入机会池时价格'")
    private Double initPrice;
    @Column(columnDefinition = "double comment'当前价格'")
    private Double currentPrice;
    @Column(columnDefinition = "varchar(255) comment'板块、概念'")
    private String tags;

}
