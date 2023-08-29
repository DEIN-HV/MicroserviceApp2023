package com.vinhdien.borrowingservice.command.controller;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinhdien.borrowingservice.command.command.CreateBorrowCommand;
import com.vinhdien.borrowingservice.command.model.BorrowRequestModel;

@RestController
@RequestMapping("/api/v1/borrowing")
public class BorrowCommandController {
	
	@Autowired
	private CommandGateway commandGateway;
	
	@PostMapping
	public String addBorrowing(@RequestBody BorrowRequestModel model) {
		try {
			CreateBorrowCommand command = new CreateBorrowCommand(UUID.randomUUID().toString(), model.getBookId(), model.getEmployeeId(), model.getBorrowingDate());
			commandGateway.sendAndWait(command);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "added borrowing";
	}
	
}
