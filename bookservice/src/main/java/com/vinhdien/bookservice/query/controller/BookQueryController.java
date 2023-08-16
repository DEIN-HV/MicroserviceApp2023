package com.vinhdien.bookservice.query.controller;

import java.util.List;

import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinhdien.bookservice.query.model.BookResponseModel;
import com.vinhdien.bookservice.query.queries.GetAllBooksQuery;
import com.vinhdien.bookservice.query.queries.GetBookQuery;

@RestController
@RequestMapping("/api/v1/books")
public class BookQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping("/{bookId}")
    public BookResponseModel getBookDetail(@PathVariable String bookId) {
        GetBookQuery getBookQuery = new GetBookQuery();
        getBookQuery.setBookId(bookId);

        BookResponseModel bookResponseModel = queryGateway
                .query(getBookQuery, ResponseTypes.instanceOf(BookResponseModel.class))
                .join();
        return bookResponseModel;
    }

    @GetMapping
    public List<BookResponseModel> getAllBooks() {
        GetAllBooksQuery getBookQuery = new GetAllBooksQuery();
        List<BookResponseModel> list = queryGateway
                .query(getBookQuery, ResponseTypes.multipleInstancesOf(BookResponseModel.class))
                .join();
        return list;
    }

}
