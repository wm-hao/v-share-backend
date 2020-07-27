package zhh.share.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author richer
 * @date 2020/7/26 9:02 上午
 */
@Entity
@Data
public class BalanceChange extends BaseEntity {

    @Column(columnDefinition = "varchar(10) comment '变更类型'")
    private String changeType;

    @Column(columnDefinition = "double comment '变更金额'")
    private Double changeAmount;

    @Column(columnDefinition = "varchar(10) comment '变更类型'")
    private String balanceType;

    @Column(columnDefinition = "double comment '变更前金额'")
    private Double preBalance;

    @Column(columnDefinition = "bigint comment '账本主键'")
    private Long balanceId;

    @Column(columnDefinition = "bigint comment '用户'")
    private Long userId;
}
