package zhh.share.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author richer
 * @date 2020/7/31 10:10 下午
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@ToString(callSuper = true)
public class Journal extends BaseEntity{

    @Column(columnDefinition = "varchar(500) comment '标签'")
    private String tags;

    @Column(columnDefinition = "varchar(500) comment '标题'")
    private String title;

    @Column(columnDefinition = "varchar(500) comment '副标题'")
    private String subTitle;

    @Column(columnDefinition = "text comment '内容'")
    private String content;
}
