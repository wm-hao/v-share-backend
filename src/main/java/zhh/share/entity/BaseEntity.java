package zhh.share.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author richer
 * @date 2020/7/25 9:46 下午
 */
@MappedSuperclass
@Data
public class BaseEntity {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "bigint comment '主键'", nullable = false)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "datetime comment '创建时间' ")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "datetime comment '更新时间' ")
    private Date updateTime;

    @Column(columnDefinition = "int(1) comment '状态' default 1")
    private Integer state;

    @Column(columnDefinition = "varchar(400) comment '备注'")
    private String remarks;

    @Column(columnDefinition = "varchar(10) comment '日期'")
    private String date;


    @Column(columnDefinition = "bigint comment '用户编号'")
    private Long userId;

    @Transient
    public static final String ID = "id";
    @Transient
    public static final String CREATE_TIME = "createTime";
    @Transient
    public static final String UPDATE_TIME = "updateTime";
    @Transient
    public static final String STATE = "state";
    @Transient
    public static final String REMARKS = "remarks";
    @Transient
    public static final String DATE = "date";
    @Transient
    public static final String USER_ID = "userId";
}
