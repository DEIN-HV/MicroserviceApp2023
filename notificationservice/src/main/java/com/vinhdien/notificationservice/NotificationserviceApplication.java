package com.vinhdien.notificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableBinding(Sink.class)
public class NotificationserviceApplication {
	
	@StreamListener(Sink.INPUT) 
	public void consumeMessage(Message message) {
		System.out.println("message" + message);
		
//		EmployeeReponseModel Employeemodel = circuitBreakerFactory.create("getEmployee").run(
//				() -> { EmployeeReponseModel model = webClientBuilder.build()
//						.get()
//						.uri("http://localhost:9002/api/v1/employees/"+message.getEmployeeId())
//						.retrieve()
//						.bodyToMono(EmployeeReponseModel.class)
//						.block();
//				return model;
//				},
//				t -> { EmployeeReponseModel model = new EmployeeReponseModel();
//						model.setFirstName("Anonymous");
//						model.setLastName("Employee");
//						return model;
//				}
//					);
//				
//				if(Employeemodel !=null) {
//					logger.info("Consume Payload: "+Employeemodel.getFirstName()+ " "+Employeemodel.getLastName()+" "+message.getMessage());
//				}
	}

	public static void main(String[] args) {
		SpringApplication.run(NotificationserviceApplication.class, args);
	}

}
