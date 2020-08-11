package zhh.share.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zhh.share.constant.CommonConstant;
import zhh.share.dao.BookRepository;
import zhh.share.entity.Book;
import zhh.share.service.BookService;
import zhh.share.util.CommonUtil;
import zhh.share.util.TimeUtil;

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
            return bookRepository.save(book);
        } else {
            throw new Exception("书籍信息不存在");
        }
    }

    @Override
    public Book addNewBook(Book book) throws Exception {
        CommonUtil.fillDefaultProps(book);
        book.setReadCounts(0);
        return bookRepository.save(book);
    }
}
