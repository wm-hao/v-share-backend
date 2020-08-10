package zhh.share.service;

import zhh.share.entity.Book;

import java.util.List;

/**
 * @author richer
 * @date 2020/8/10 5:54 下午
 */
public interface BookService {

    List<Book> findByUserId(long userId) throws Exception;

    Book update(Book book) throws Exception;

    Book addNewBook(Book book) throws Exception;
}
