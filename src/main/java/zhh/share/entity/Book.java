package zhh.share.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author richer
 * @date 2020/8/10 5:42 下午
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@ToString(callSuper = true)
public class Book extends BaseEntity {

    @Column(columnDefinition = "varchar(500) comment '书名'", nullable = false)
    private String name;
    @Column(columnDefinition = "bigint(20) comment '用户编码'", nullable = false)
    private Long userId;
    @Column(columnDefinition = "text comment '书评'")
    private String note;
    @Column(columnDefinition = "int(3) comment '进度'", nullable = false)
    private Integer progress;
    @Column(columnDefinition = "int(10) comment '总页数'", nullable = false)
    private Integer totalPages;
}
