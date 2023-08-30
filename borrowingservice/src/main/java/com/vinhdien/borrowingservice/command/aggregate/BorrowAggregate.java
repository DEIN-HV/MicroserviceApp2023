package com.vinhdien.borrowingservice.command.aggregate;

import java.util.Date;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.vinhdien.borrowingservice.command.command.CreateBorrowCommand;
import com.vinhdien.borrowingservice.command.event.BorrowCreatedEvent;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

@Aggregate
public class BorrowAggregate {
    @AggregateIdentifier
    private String id;

    private String bookId;
    private String employeeId;
    private Date borrowingDate;
    private Date returnDate;

    private String message;

    public BorrowAggregate() {
    }

    @CommandHandler
    public BorrowAggregate(CreateBorrowCommand command) {
        BorrowCreatedEvent event = new BorrowCreatedEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventHandler
    public void on(BorrowCreatedEvent event) {
		this.bookId = event.getBookId();
		this.borrowingDate = event.getBorrowingDate();
		this.employeeId = event.getEmployeeId();
		this.id = event.getId();
	}

}
