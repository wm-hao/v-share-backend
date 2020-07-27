package zhh.share.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @author richer
 * @date 2020/7/23 10:35 上午
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profit {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "bigint(20) comment '主键'", nullable = false)
    private Long id;

    @Column(columnDefinition = "varchar(100) comment '股票名称'", nullable = false)
    private String shareName;

    @Column(columnDefinition = "varchar(50) comment '股票代码'", nullable = false)
    private String shareCode;

    @Column(columnDefinition = "double comment '持股数量'", nullable = false)
    private Double keepCount;

    @Column(columnDefinition = "double comment '持仓总额'")
    private Double keepAmount;

    @Column(columnDefinition = "double comment '利润总额'")
    private Double profit;

    @Column(columnDefinition = "int comment '累计持股天数'")
    private Integer keepDays;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "datetime comment '创建时间'")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "datetime comment '更新时间'")
    private Date updateTime;

    @Column(columnDefinition = "int(1) comment '状态'")
    private Integer state;

    @Transient
    public static final String CREATE_TIME = "createTime";
    @Transient
    public static final String UPDATE_TIME = "updateTime";
    @Transient
    public static final String ID = "id";
    @Transient
    public static final String STATE = "state";
    @Transient
    public static final String SHARE_NAME = "shareName";
    @Transient
    public static final String SHARE_CODE = "shareCode";
    @Transient
    public static final String KEEP_COUNT = "keepCount";
    @Transient
    public static final String KEEP_AMOUNT = "keepAmount";
    @Transient
    public static final String PROFIT = "profit";
    @Transient
    public static final String KEEP_DAYS = "keepDays";

}
