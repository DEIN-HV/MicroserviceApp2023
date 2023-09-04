package com.vinhdien.borrowingservice.command.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vinhdien.borrowingservice.command.data.BorrowRepository;

@Service
public class BorrowService {
	@Autowired
	BorrowRepository borrowRepository;

//	public String findIdBorrowing(String employeeId, String bookId) {
//
//		return borrowRepository.findByEmployeeIdAndBookIdAndReturnDateIsNull(employeeId, bookId).getId();
//	}
}
