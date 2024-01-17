package by.toronchenko.taskn1.service;

import by.toronchenko.taskn1.dto.UserDto;
import by.toronchenko.taskn1.entity.Company;
import by.toronchenko.taskn1.entity.User;
import by.toronchenko.taskn1.repositories.UserRepository;
import by.toronchenko.taskn1.util.exception.NoCompanyFoundException;
import by.toronchenko.taskn1.util.exception.NoUserFoundException;
import by.toronchenko.taskn1.util.exception.NotFoundException;
import by.toronchenko.taskn1.validators.Error;
import by.toronchenko.taskn1.validators.UserValidator;
import by.toronchenko.taskn1.validators.ValidationResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserRepository userRepository;
	@Mock
	private UserValidator userValidator;
	@InjectMocks
	private UserService userService;

	@Test
	void testSaveUser(){
		Company company = Company.builder()
				.name("Test")
				.build();
		User user = User.builder()
				.id(1L)
				.name("Testoviy")
				.password("test")
				.build();
		company.addUser(user);

		UserDto userDto = new UserDto(1L, "Testoviy", "test", company, new ArrayList<>());

		when(userValidator.isValid(user)).thenReturn(new ValidationResult());
		when(userRepository.save(user)).thenReturn(user);

		UserDto result = userService.saveUser(user);

		assertEquals(userDto, result);
	}

	@Test
	void testSaveNotValidUser(){
		Company company = Company.builder()
				.name("Test")
				.build();
		User user = User.builder()
				.id(null)
				.name("")
				.password("")
				.build();
		company.addUser(user);
		ValidationResult validationResult = new ValidationResult();
		validationResult.add(Error.of("invalid.login", "Login is empty!"));
		validationResult.add(Error.of("invalid.password", "Password is empty!"));

		UserDto userDto = new UserDto(null, null, null, null,validationResult.getErrors());

		when(userValidator.isValid(user)).thenReturn(validationResult);

		UserDto result = userService.saveUser(user);

		assertEquals(userDto, result);
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

		when(userRepository.findById(1L)).thenReturn(Optional.of(user));

		User result = userService.findUserById(1L);

		assertEquals(user, result);
	}

	@Test
	void testFindUserByIdWithException(){
		when(userRepository.findById(10L)).thenThrow(NoUserFoundException.class);
		assertThrows(NoUserFoundException.class, () -> userService.findUserById(10L));
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

		when(userRepository.findAll()).thenReturn(users);

		Iterable<User> result = userService.findAllUsers();

		assertEquals(users, result);
	}

	@Test
	void testFindPageUsersByName() {
		Company company = Company.builder()
				.name("Test")
				.build();
		User user = User.builder()
				.name("Testoviy")
				.password("test")
				.build();
		company.addUser(user);
		List<User> users = new ArrayList<>();
		users.add(user);

		when(userRepository.findDistinctFirstByName(PageRequest.of(1, 5), "Testoviy"))
				.thenReturn(new PageImpl<>(users));
		Page<User> result = userService.findPageUsersByName(PageRequest.of(1, 5), "Testoviy");
		assertEquals(users, result.stream().toList());
	}

	@Test
	void testFindPageUsersByEmptyName() {
		Company company = Company.builder()
				.name("Test")
				.build();
		User user = User.builder()
				.name("Testoviy")
				.password("test")
				.build();
		company.addUser(user);
		Company company2 = Company.builder()
				.name("Test")
				.build();
		User user2 = User.builder()
				.name("Testoviy")
				.password("test")
				.build();
		company.addUser(user);
		company2.addUser(user2);
		List<User> users = new ArrayList<>();
		users.add(user);
		users.add(user2);

		when(userRepository.findAll(PageRequest.of(1, 5)))
				.thenReturn(new PageImpl<>(users));
		Page<User> result = userService.findPageUsersByName(PageRequest.of(1, 5), "");
		assertEquals(users, result.stream().toList());
	}

	@Test
	void testDeleteUserById() {
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

		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		userService.deleteUserById(1L);
		verify(userRepository, Mockito.times(1)).deleteById(1L);
	}

	@Test
	void testDeleteUserByIdWithException(){
		when(userRepository.findById(10L)).thenThrow(NoUserFoundException.class);
		assertThrows(NoUserFoundException.class, () -> userService.deleteUserById(10L));
	}

	@Test
	void testDeleteUser(){
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

		when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
		userService.deleteUser(user);
		verify(userRepository, Mockito.times(1)).delete(user);
	}

	@Test
	void testDeleteUserWithException() {
		Company company = Company.builder()
				.company_id(10L)
				.name("Test")
				.build();
		User user = User.builder()
				.id(10L)
				.name("Testoviy")
				.password("test")
				.build();
		company.addUser(user);

		when(userRepository.findById(user.getId())).thenThrow(NoUserFoundException.class);
		assertThrows(NoUserFoundException.class, () -> userService.deleteUser(user));
	}

}
