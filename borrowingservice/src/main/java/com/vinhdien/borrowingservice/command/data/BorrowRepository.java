package com.vinhdien.borrowingservice.command.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowRepository extends JpaRepository<Borrowing, String> {

}
