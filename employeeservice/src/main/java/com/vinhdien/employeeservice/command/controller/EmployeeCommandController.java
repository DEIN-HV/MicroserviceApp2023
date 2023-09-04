package com.vinhdien.employeeservice.command.controller;

import java.util.UUID;

import javax.xml.transform.Source;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.messaging.MessageChannel;

import com.vinhdien.employeeservice.command.command.CreateEmployeeCommand;
import com.vinhdien.employeeservice.command.command.DeleteEmployeeCommand;
import com.vinhdien.employeeservice.command.command.UpdateEmployeeCommand;
import com.vinhdien.employeeservice.command.model.EmployeeRequestModel;

@RestController
@RequestMapping("/api/v1/employees")
@EnableBinding(Source.class)
public class EmployeeCommandController {

    @Autowired
    private CommandGateway commandGateway;
//    
//	@Autowired
//	private MessageChannel output;

    @PostMapping
    public String addEmployee(@RequestBody EmployeeRequestModel model) {
        CreateEmployeeCommand command = new CreateEmployeeCommand(UUID.randomUUID().toString(), model.getFirstName(),
                model.getKin(), model.getLastName(), true);
        commandGateway.sendAndWait(command);
        return "added employee";
    }

    @PutMapping
    public String updateEmployee(@RequestBody EmployeeRequestModel model) {
        UpdateEmployeeCommand command = new UpdateEmployeeCommand(model.getEmployeeId(), model.getFirstName(),
                model.getLastName(), model.getKin(), model.getIsDisciplined());
        commandGateway.sendAndWait(command);
        return "employee updated";
    }

    @DeleteMapping
    public String deleteEmployee(@PathVariable String employeeId) {
        DeleteEmployeeCommand command = new DeleteEmployeeCommand(employeeId);
        commandGateway.sendAndWait(command);
        return "employee deleted";
    }
    
//	@PostMapping("/sendMessage")
//	public void SendMessage(@RequestBody String message) {
//		try {
//			
//			ObjectMapper mapper = new ObjectMapper();
//			String json = mapper.writeValueAsString(message);
//			output.send(MessageBuilder.withPayload(json).build());
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
