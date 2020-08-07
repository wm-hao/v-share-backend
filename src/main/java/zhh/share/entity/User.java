package zhh.share.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @author richer
 * @date 2020/7/22 8:35 上午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(indexes = {@Index(columnList = "userName", name = "idx_userName")})
public class User {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "bigint(20) comment '主键'", nullable = false)
    private Long id;

    @Column(columnDefinition = "varchar(100) comment '用户名'", nullable = false, unique = true)
    private String userName;

    @Column(columnDefinition = "varchar(200) comment '密码'", nullable = false)
    private String password;

    @Column(columnDefinition = "int(1) comment '状态'")
    private Integer state;

    @Column(columnDefinition = "varchar(200) comment '邮箱'", nullable = false)
    private String email;

    @Column(columnDefinition = "varchar(400) comment '地址'")
    private String address;

    @Column(columnDefinition = "varchar(50) comment '邮编'")
    private String zip;

    @Column(columnDefinition = "varchar(32) comment '手机号'", nullable = false)
    private String billId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "datetime comment '生效日期'", nullable = false)
    private Date validDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "datetime comment '创建日期'", nullable = false)
    private Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "datetime comment '操作日期'", nullable = false)
    private Date optDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "datetime comment '过期日期'", nullable = false)
    private Date expireDate;


    @Transient
    public static final String ID = "id";
    @Transient
    public static final String USER_NAME = "userName";
    @Transient
    public static final String PASSWORD = "password";
    @Transient
    public static final String STATE = "state";
    @Transient
    public static final String EMAIL = "email";
    @Transient
    public static final String ADDRESS = "address";
    @Transient
    public static final String ZIP = "zip";
    @Transient
    public static final String BILL_ID = "billId";
    @Transient
    public static final String VALID_DATE = "validDate";
    @Transient
    public static final String CREATE_DATE = "createDate";
    @Transient
    public static final String OPT_DATE = "optDate";
    @Transient
    public static final String EXPIRE_DATE = "expireDate";

    @Transient
    private boolean encryption = true;
    @Transient
    private String verifyCode;
}
