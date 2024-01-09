package by.toronchenko.taskn1;

import by.toronchenko.taskn1.entity.Company;
import by.toronchenko.taskn1.entity.User;
import by.toronchenko.taskn1.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class TaskN1ApplicationTests {

	@Autowired
	private UserService userService;

	@Test
	@Transactional
	void testAddUser(){
		var user = User.builder()
				.name("Dima")
				.password("28102006")
				.build();
		var company = Company.builder()
				.name("Ayonis")
				.build();
		user.setCompany(company);
		userService.saveUser(user);
	}

}
