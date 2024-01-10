package by.toronchenko.taskn1.service;

import by.toronchenko.taskn1.entity.Company;
import by.toronchenko.taskn1.entity.User;
import by.toronchenko.taskn1.repositories.UserCrudRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserCrudRepository userCrudRepository;
	@InjectMocks
	private UserService userService;

	@Test
	void testSaveUser(){
		Company company = Company.builder()
				.name("Test")
				.build();
		User user = User.builder()
				.name("Testoviy")
				.password("test")
				.build();
		company.addUser(user);

		Mockito.when(userCrudRepository.save(user)).thenReturn(user);

		User result = userService.saveUser(user);

		assertEquals(user, result);
	}

	@Test
	void testFindUserById() {
		Company company = Company.builder()
				.company_id(1L)
				.name("Test")
				.build();
		User user = User.builder()
				.id(1L)
				.name("Testoviy")
				.password("test")
				.build();
		company.addUser(user);

		Mockito.when(userCrudRepository.findById(1L)).thenReturn(Optional.of(user));

		Optional<User> optionalUser = userService.findUserById(1L);

		assertEquals(user, optionalUser.get());
	}

	@Test
	void testFindAllUsers(){
		Company company = Company.builder()
				.name("Test")
				.build();
		User user = User.builder()
				.name("Testoviy")
				.password("test")
				.build();
		company.addUser(user);
		Company company2 = Company.builder()
				.name("Test2")
				.build();
		User user2 = User.builder()
				.name("Testoviy2")
				.password("test2")
				.build();
		company.addUser(user);
		company2.addUser(user2);
		List<User> users = List.of(user, user2);

		Mockito.when(userCrudRepository.findAll()).thenReturn(users);

		Iterable<User> result = userService.findAllUsers();

		assertEquals(users, result);
	}

	@Test
	void testDeleteUserById(){
		userService.deleteUserById(1L);
		Mockito.verify(userCrudRepository, Mockito.times(1)).deleteById(1L);
	}

	@Test
	void testDeleteUser(){
		Company company = Company.builder()
				.name("Test")
				.build();
		User user = User.builder()
				.name("Testoviy")
				.password("test")
				.build();
		company.addUser(user);

		userService.deleteUser(user);
		Mockito.verify(userCrudRepository, Mockito.times(1)).delete(user);
	}

}
