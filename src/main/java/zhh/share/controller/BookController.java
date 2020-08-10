package zhh.share.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zhh.share.constant.CommonConstant;
import zhh.share.dto.BaseResponse;
import zhh.share.entity.Book;
import zhh.share.service.BookService;
import zhh.share.util.CommonUtil;

import java.util.List;

/**
 * @author richer
 */
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping("/list")
    public BaseResponse getAllBooks(@RequestParam long userId) throws Exception {
        BaseResponse response = CommonUtil.success(CommonConstant.Message.QRY_SUCCESS);
        List<Book> books = bookService.findByUserId(userId);
        response.setTotal(books.size());
        response.setRows(books);
        return response;
    }

    @PostMapping("/add")
    public BaseResponse addNewBook(@RequestBody Book book) throws Exception {
        bookService.addNewBook(book);
        return CommonUtil.success(CommonConstant.Message.ADD_SUCCESS);
    }

    @PostMapping("/update")
    public BaseResponse update(@RequestBody Book book) throws Exception {
        bookService.update(book);
        return CommonUtil.success(CommonConstant.Message.UPDATE_SUCCESS);
    }
}
