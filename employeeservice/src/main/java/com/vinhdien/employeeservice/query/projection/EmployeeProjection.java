package com.vinhdien.employeeservice.query.projection;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.vinhdien.employeeservice.command.data.Employee;
import com.vinhdien.employeeservice.command.data.EmployeeRepository;
import com.vinhdien.employeeservice.query.model.EmployeeReponseModel;
import com.vinhdien.employeeservice.query.queries.GetEmployeesQuery;

public class EmployeeProjection {

    @Autowired
    EmployeeRepository employeeRepository;

    @QueryHandler
    public EmployeeReponseModel handle(GetEmployeesQuery getEmployeesQuery) {
        EmployeeReponseModel model = new EmployeeReponseModel();
        Employee employee = employeeRepository.getById(getEmployeesQuery.getEmployeeId());
        BeanUtils.copyProperties(employee, model);

        return model;
    }
}
