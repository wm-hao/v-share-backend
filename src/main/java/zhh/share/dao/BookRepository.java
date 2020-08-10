package zhh.share.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import zhh.share.entity.Book;

import java.util.List;

/**
 * @author richer
 * @date 2020/8/10 5:52 下午
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    List<Book> findByUserIdAndStateOrderByCreateTimeDesc(long userId, int state);
}
