package com.vinhdien.bookservice.command.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.vinhdien.bookservice.command.command.CreateBookCommand;
import com.vinhdien.bookservice.command.command.DeleteBookCommand;
import com.vinhdien.bookservice.command.command.UpdateBookCommand;
import com.vinhdien.bookservice.command.event.BookCreatedEvent;
import com.vinhdien.bookservice.command.event.BookDeleteEvent;
import com.vinhdien.bookservice.command.event.BookUpdateEvent;
import com.vinhdien.commonservice.command.RollBackStatusBookCommand;
import com.vinhdien.commonservice.command.UpdateStatusBookCommand;
import com.vinhdien.commonservice.event.BookRollBackStatusEvent;
import com.vinhdien.commonservice.event.BookUpdateStatusEvent;

@Aggregate
public class BookAggregate {
	@AggregateIdentifier
	private String bookId;
	private String name;
	private String author;
	private Boolean isReady;

	public BookAggregate() {
	}

	@CommandHandler
	public BookAggregate(CreateBookCommand createBookCommand) {
		BookCreatedEvent bookCreatedEvent = new BookCreatedEvent();
		BeanUtils.copyProperties(createBookCommand, bookCreatedEvent);
		AggregateLifecycle.apply(bookCreatedEvent);
	}

	@CommandHandler
	public void handle(UpdateBookCommand updateBookCommand) {
		BookUpdateEvent bookUpdateEvent = new BookUpdateEvent();
		BeanUtils.copyProperties(updateBookCommand, bookUpdateEvent);
		AggregateLifecycle.apply(bookUpdateEvent);
	}

	@CommandHandler
	public void handle(DeleteBookCommand deleteBookCommand) {
		BookDeleteEvent bookDeleteEvent = new BookDeleteEvent();
		BeanUtils.copyProperties(deleteBookCommand, bookDeleteEvent);
		AggregateLifecycle.apply(bookDeleteEvent);
	}

	@EventSourcingHandler
	public void on(BookCreatedEvent event) {
		this.bookId = event.getBookId();
		this.author = event.getAuthor();
		this.isReady = event.getIsReady();
		this.name = event.getName();
	}

	@EventSourcingHandler
	public void on(BookUpdateEvent event) {
		this.bookId = event.getBookId();
		this.author = event.getAuthor();
		this.isReady = event.getIsReady();
		this.name = event.getName();
	}

	@CommandHandler
	public void handle(UpdateStatusBookCommand command) {
		BookUpdateStatusEvent event = new BookUpdateStatusEvent();
		BeanUtils.copyProperties(command, event);
		AggregateLifecycle.apply(event);
	}

	@EventSourcingHandler
	public void on(BookUpdateStatusEvent event) {
		this.bookId = event.getBookId();
		this.isReady = event.getIsReady();
	}

	@CommandHandler
	public void handle(RollBackStatusBookCommand command) {
		BookRollBackStatusEvent event = new BookRollBackStatusEvent();
		BeanUtils.copyProperties(command, event);
		AggregateLifecycle.apply(event);
	}

	@EventSourcingHandler
	public void on(BookRollBackStatusEvent event) {
		this.bookId = event.getBookId();
		this.isReady = event.getIsReady();
	}
}
