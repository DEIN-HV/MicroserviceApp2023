package com.vinhdien.bookservice.query.projection;

import java.util.ArrayList;
import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vinhdien.bookservice.command.data.Book;
import com.vinhdien.bookservice.command.data.BookRepository;
import com.vinhdien.bookservice.query.model.BookResponseModel;
import com.vinhdien.bookservice.query.queries.GetAllBooksQuery;
import com.vinhdien.bookservice.query.queries.GetBookQuery;
import com.vinhdien.commonservice.model.BookResponseCommonModel;
import com.vinhdien.commonservice.query.GetDetailsBookQuery;

@Component
public class BookProjection {

	@Autowired
	BookRepository bookRepository;

	@QueryHandler
	public BookResponseModel handle(GetBookQuery getBookQuery) {
		BookResponseModel bookResponseModel = new BookResponseModel();
		Book book = bookRepository.getById(getBookQuery.getBookId());
		BeanUtils.copyProperties(book, bookResponseModel);
		return bookResponseModel;
	}

	@QueryHandler
	public List<BookResponseModel> handle(GetAllBooksQuery getAllBooksQuery) {
		List<BookResponseModel> listBookResponse = new ArrayList<>();
		List<Book> listBook = bookRepository.findAll();
		listBook.forEach(s -> {
			BookResponseModel model = new BookResponseModel();
			BeanUtils.copyProperties(s, model);
			listBookResponse.add(model);
		});
		return listBookResponse;
	}

	@QueryHandler
	public BookResponseCommonModel handle(GetDetailsBookQuery getDetailsBookQuery) {
		BookResponseCommonModel model = new BookResponseCommonModel();
		Book book = bookRepository.getById(getDetailsBookQuery.getBookId());
		BeanUtils.copyProperties(book, model);

		return model;
	}

}
