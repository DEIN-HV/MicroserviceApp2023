package com.vinhdien.employeeservice.query.projection;

import java.util.ArrayList;
import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vinhdien.commonservice.model.EmployeeResponseCommonModel;
import com.vinhdien.commonservice.query.GetDetailsEmployeeQuery;
import com.vinhdien.employeeservice.command.data.Employee;
import com.vinhdien.employeeservice.command.data.EmployeeRepository;
import com.vinhdien.employeeservice.query.model.EmployeeReponseModel;
import com.vinhdien.employeeservice.query.queries.GetAllEmployeeQuery;
import com.vinhdien.employeeservice.query.queries.GetEmployeesQuery;

@Component
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

	@QueryHandler
	public List<EmployeeReponseModel> handle(GetAllEmployeeQuery getallAllEmployeeQuery) {
		List<EmployeeReponseModel> modelList = new ArrayList<>();
		List<Employee> list = employeeRepository.findAll();
		list.forEach(s -> {
			EmployeeReponseModel model = new EmployeeReponseModel();
			BeanUtils.copyProperties(s, model);
			modelList.add(model);
		});
		return modelList;
	}

	@QueryHandler
	public EmployeeResponseCommonModel handle(GetDetailsEmployeeQuery getDetailsEmployeeQuery) {
		EmployeeResponseCommonModel model = new EmployeeResponseCommonModel();
		Employee employee = employeeRepository.getById(getDetailsEmployeeQuery.getEmployeeId());
		BeanUtils.copyProperties(employee, model);

		return model;
	}
}
