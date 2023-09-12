package com.vinhdien.borrowingservice.command.saga;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import com.vinhdien.borrowingservice.command.command.DeleteBorrowCommand;
import com.vinhdien.borrowingservice.command.command.SendMessageCommand;
import com.vinhdien.borrowingservice.command.event.BorrowCreatedEvent;
import com.vinhdien.borrowingservice.command.model.EmployeeResponseCommonModel;
import com.vinhdien.commonservice.command.RollBackStatusBookCommand;
import com.vinhdien.commonservice.command.UpdateStatusBookCommand;
import com.vinhdien.commonservice.event.BookUpdateStatusEvent;
import com.vinhdien.commonservice.model.BookResponseCommonModel;
import com.vinhdien.commonservice.query.GetDetailsBookQuery;
import com.vinhdien.commonservice.query.GetDetailsEmployeeQuery;

@Saga
public class BorrowingSaga {
	@Autowired
	private transient CommandGateway commandGateway;

	@Autowired
	private transient QueryGateway queryGateway;

	@StartSaga
	@SagaEventHandler(associationProperty = "id")
	private void handle(BorrowCreatedEvent event) {
		System.out.println("BorrowCreatedEvent in Saga for BookId : " + event.getBookId() + " : EmployeeId :  "
				+ event.getEmployeeId());

		try {
			SagaLifecycle.associateWith("bookId", event.getBookId());

			GetDetailsBookQuery getDetailsBookQuery = new GetDetailsBookQuery(event.getBookId());

			BookResponseCommonModel bookResponseModel = queryGateway
					.query(getDetailsBookQuery, ResponseTypes.instanceOf(BookResponseCommonModel.class)).join();
			if (bookResponseModel.getIsReady() == true) {
				UpdateStatusBookCommand command = new UpdateStatusBookCommand(event.getBookId(), false,
						event.getEmployeeId(), event.getId());
				commandGateway.sendAndWait(command);
			} else {

				throw new Exception("Sach Da co nguoi Muon");
			}

		} catch (Exception e) {
			rollBackBorrowRecord(event.getId());

			System.out.println(e.getMessage());
		}
	}

	@SagaEventHandler(associationProperty = "bookId")
	private void handle(BookUpdateStatusEvent event) {
		System.out.println("BookUpdateStatusEvent in Saga for BookId : " + event.getBookId());
		try {
			GetDetailsEmployeeQuery getDetailsEmployeeQuery = new GetDetailsEmployeeQuery(event.getEmployeeId());

			EmployeeResponseCommonModel employeeResponseCommonModel = queryGateway.query(getDetailsEmployeeQuery,
					ResponseTypes.instanceOf(EmployeeResponseCommonModel.class))
					.join();
			if (employeeResponseCommonModel.getIsDisciplined() == true) {
				throw new Exception("Nhan vien bi ky luat");
			} else {
				commandGateway.sendAndWait(new SendMessageCommand(event.getBorrowId(), event.getEmployeeId(),
						"Da muon sach thanh cong !"));
				SagaLifecycle.end();
			}
		} catch (Exception e) {

			System.out.println(e.getMessage());
			rollBackBookStatus(event.getBookId(), event.getEmployeeId(), event.getBorrowId());
		}
	}

	private void rollBackBookStatus(String bookId, String employeeId, String borrowId) {
		SagaLifecycle.associateWith("bookId", bookId);
		RollBackStatusBookCommand command = new RollBackStatusBookCommand(bookId, true, employeeId, borrowId);
		commandGateway.sendAndWait(command);
	}

	private void rollBackBorrowRecord(String id) {
		commandGateway.sendAndWait(new DeleteBorrowCommand(id));
	}
}
