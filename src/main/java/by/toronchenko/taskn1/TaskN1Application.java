package by.toronchenko.taskn1;

import by.toronchenko.taskn1.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@SpringBootApplication
public class TaskN1Application {

	public static void main(String[] args) {
		SpringApplication.run(TaskN1Application.class, args);
	}

}
