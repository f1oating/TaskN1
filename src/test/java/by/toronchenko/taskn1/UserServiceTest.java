package by.toronchenko.taskn1;

import by.toronchenko.taskn1.entity.Company;
import by.toronchenko.taskn1.entity.User;
import by.toronchenko.taskn1.service.CompanyService;
import by.toronchenko.taskn1.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserServiceTest {

	@Autowired
	private UserService userService;

	@Autowired
	private CompanyService companyService;

	@Test
	public void testGetUserById() {
		Long userId = 11L;
		Company company = Company.builder()
				.company_id(4L)
				.name("Toronchenko INC")
				.build();
		User user = User.builder()
				.id(userId)
				.name("Vova")
				.password("1920")
				.build();
		company.addUser(user);

		User result = userService.findUserById(userId).get();

		assertEquals(user, result);

		userService.deleteUser(user);
	}

	@Test
	public void testCreateUser() {
		Company company = Company.builder()
				.name("Test")
				.build();
		User user = User.builder()
				.name("Testoviy")
				.password("test")
				.build();
		company.addUser(user);

		Company savedCompany = Company.builder()
				.company_id(6L)
				.name("Test")
				.build();
		User savedUser = User.builder()
				.id(16L)
				.name("Testoviy")
				.password("test")
				.build();
		company.addUser(user);

		Company result = companyService.saveCompany(company);

		assertEquals(savedCompany, result);

		companyService.deleteCompany(company);
	}
}
