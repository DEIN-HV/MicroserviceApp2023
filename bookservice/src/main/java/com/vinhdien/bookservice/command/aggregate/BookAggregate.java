package com.vinhdien.bookservice.command.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.vinhdien.bookservice.command.command.CreateBookCommand;
import com.vinhdien.bookservice.command.event.BookCreatedEvent;

@Aggregate
public class BookAggregate {
	@AggregateIdentifier
	private String bookId;
	private String name;
	private String author;
	private Boolean isReady;
	
	 public BookAggregate() {}
	 
	 @CommandHandler
	    public BookAggregate(CreateBookCommand createBookCommand) {  
	        BookCreatedEvent bookCreatedEvent = new BookCreatedEvent();
	        BeanUtils.copyProperties(createBookCommand,bookCreatedEvent);
	        AggregateLifecycle.apply(bookCreatedEvent);
	    }
	 
	 @EventSourcingHandler
	    public void on(BookCreatedEvent event) {
			this.bookId = event.getBookId();
			this.author = event.getAuthor();
			this.isReady = event.getIsReady();
			this.name = event.getName();
	    }
}
