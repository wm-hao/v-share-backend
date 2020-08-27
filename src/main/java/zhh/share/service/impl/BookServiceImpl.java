package zhh.share.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zhh.share.constant.CommonConstant;
import zhh.share.dao.BookRepository;
import zhh.share.entity.Balance;
import zhh.share.entity.BaseEntity;
import zhh.share.entity.Book;
import zhh.share.service.BookService;
import zhh.share.util.CommonUtil;
import zhh.share.util.TimeUtil;

import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * @author richer
 * @date 2020/8/10 5:55 下午
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Override
    public List<Book> findByUserId(long userId) throws Exception {
        return bookRepository.findByUserIdAndStateOrderByCreateTimeDesc(userId, CommonConstant.State.STATE_VALID);
    }

    @Override
    public Book update(Book book) throws Exception {
        if (bookRepository.existsById(book.getId())) {
            book.setUpdateTime(TimeUtil.getCurrentTimestamp());
            transferBook(book);
            return bookRepository.save(book);
        } else {
            throw new Exception("书籍信息不存在");
        }
    }

    @Override
    public Book addNewBook(Book book) throws Exception {
        CommonUtil.fillDefaultProps(book);
        book.setReadCounts(0);
        transferBook(book);
        return bookRepository.save(book);
    }

    @Override
    public Page<Book> findByUserIdPagination(long userId, int page, int size) throws Exception {
        return bookRepository.findAll((Specification<Book>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            predicate.getExpressions().add(criteriaBuilder.equal(root.get(Balance.USER_ID), userId));
            predicate.getExpressions().add(criteriaBuilder.equal(root.get(BaseEntity.STATE), CommonConstant.State.STATE_VALID));
            return predicate;
        }, PageRequest.of(page, size, Sort.Direction.DESC, BaseEntity.CREATE_TIME));
    }

    private void transferBook(Book book) {
        if (book != null) {
            if (book.getProgress() == 99) {
                book.setProgress(100);
                int readCounts = book.getReadCounts() == null ? 0 : book.getReadCounts();
                book.setReadCounts(readCounts + 1);
            }
        }
    }
}
