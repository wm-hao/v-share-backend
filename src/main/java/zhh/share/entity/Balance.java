package zhh.share.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * @author richer
 * @date 2020/7/25 9:50 下午
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@ToString(callSuper = true)
public class Balance extends BaseEntity {

    @Column(columnDefinition = "double comment '现金'")
    private Double cashAmount;

    @Column(columnDefinition = "double comment '基金'")
    private Double fundAmount;

    @Column(columnDefinition = "double comment '股票'")
    private Double shareAmount;

    @Column(columnDefinition = "double comment '合计'")
    private Double total;

    @Column(columnDefinition = "bigint comment '用户编号'")
    private Long userId;

    @Column(columnDefinition = "varchar(8) comment '日期'")
    private String date;

    @Column(columnDefinition = "double comment '利润'")
    private Double profit;

    @Transient
    public static String USER_ID = "userId";
}
