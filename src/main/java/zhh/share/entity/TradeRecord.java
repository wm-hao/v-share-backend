package zhh.share.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author richer
 * @date 2020/7/21 9:45 上午
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Data
@ToString(callSuper = true)
@Table(indexes = {@Index(columnList = "shareName")})
public class TradeRecord extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "bigint(20) comment '主键'", nullable = false)
    private Long id;

    @Column(columnDefinition = "varchar(100) comment '股票名称'", nullable = false)
    private String shareName;

    @Column(columnDefinition = "varchar(50) comment '股票代码'", nullable = false)
    private String shareCode;


    @Column(columnDefinition = "varchar(50) comment '拼音简称'")
    private String alias;


    @Column(columnDefinition = "double comment '交易价格'", nullable = false)
    private Double unitPrice;

    @Column(columnDefinition = "double comment '交易数量'", nullable = false)
    private Double payCount;

    @Column(columnDefinition = "double comment '交易金额'", nullable = false)
    private Double payAmount;

    @Column(columnDefinition = "varchar(10) comment '操作类型'", nullable = false)
    private String payType;

    @Column(columnDefinition = "varchar(100) comment '所属交易所'")
    private String stockExchange;

    @Column(columnDefinition = "double comment '手续费'", nullable = false)
    private Double fee;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(columnDefinition = "datetime comment '交易时间'")
    private Date payTime;

    @Column(columnDefinition = "varchar(200) comment '交易流水'")
    private String tradeSeq;

    @Transient
    public static final String SHARE_NAME = "shareName";
    @Transient
    public static final String SHARE_CODE = "shareCode";
    @Transient
    public static final String UNIT_PRICE = "unitPrice";
    @Transient
    public static final String PAY_COUNT = "payCount";
    @Transient
    public static final String PAY_AMOUNT = "payAmount";
    @Transient
    public static final String PAY_TYPE = "payType";
    @Transient
    public static final String SOCKET_EXCHANGE = "stockExchange";
    @Transient
    public static final String FEE = "fee";
    @Transient
    public static final String ID = "id";
    @Transient
    public static final String CREATE_TIME = "createTime";
    @Transient
    public static final String UPDATE_TIME = "updateTime";
    @Transient
    public static final String STATE = "state";
    @Transient
    public static final String PAY_TIME = "payTime";
    @Transient
    public static final String ALIAS = "alias";
    @Transient
    public static final String TRADE_SEQ = "tradeSeq";
    @Transient
    public static final String USER_ID = "userId";

}
