package com.vinhdien.bookservice.command.event;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vinhdien.bookservice.command.data.Book;
import com.vinhdien.bookservice.command.data.BookRepository;
import com.vinhdien.commonservice.event.BookRollBackStatusEvent;
import com.vinhdien.commonservice.event.BookUpdateStatusEvent;

@Component
public class BookEventsHandler {

	@Autowired
	private BookRepository bookRepository;

	@EventHandler
	public void on(BookCreatedEvent event) {
		Book book = new Book();
		BeanUtils.copyProperties(event, book);
		bookRepository.save(book);
	}

	@EventHandler
	public void on(BookUpdateEvent event) {
		Book book = bookRepository.getById(event.getBookId());
		book.setName(event.getName());
		book.setAuthor(event.getAuthor());
		book.setIsReady(event.getIsReady());
		bookRepository.save(book);
	}

	@EventHandler
	public void on(BookDeleteEvent event) {
		Book book = bookRepository.getById(event.getBookId());
		bookRepository.delete(book);
	}

	@EventHandler
	public void on(BookUpdateStatusEvent event) {
		Book book = bookRepository.getById(event.getBookId());
		book.setIsReady(event.getIsReady());
		bookRepository.save(book);
	}

	@EventHandler
	public void on(BookRollBackStatusEvent event) {
		Book book = bookRepository.getById(event.getBookId());
		book.setIsReady(event.getIsReady());
		bookRepository.save(book);
	}
}
