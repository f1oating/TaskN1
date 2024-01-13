package by.toronchenko.taskn1.service;

import by.toronchenko.taskn1.dto.UserDto;
import by.toronchenko.taskn1.entity.User;
import by.toronchenko.taskn1.repositories.UserRepository;
import by.toronchenko.taskn1.util.exception.NoCompanyFoundException;
import by.toronchenko.taskn1.util.exception.NoUserFoundException;
import by.toronchenko.taskn1.validators.UserValidator;
import by.toronchenko.taskn1.validators.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserValidator userValidator;

    public UserDto saveUser(User user){
        ValidationResult validationResult = userValidator.isValid(user);
        return !validationResult.isValid()
                ? toUserDto(userRepository.save(user))
                : new UserDto(null, null, null, null, validationResult.getErrors());
    }

    public User findUserById(Long id) throws NoUserFoundException {
        return this.userRepository.findById(id)
                .orElseThrow(NoUserFoundException::new);
    }

    public Iterable<User> findAllUsers(){
        return userRepository.findAll();
    }

    public void deleteUserById(Long id) throws NoUserFoundException {
        this.userRepository.findById(id)
                .orElseThrow(NoUserFoundException::new);
        this.userRepository.deleteById(id);
    }

    public void deleteUser(User user) throws NoUserFoundException {
        this.userRepository.findById(user.getId())
                .orElseThrow(NoUserFoundException::new);
        this.userRepository.delete(user);
    }

    private UserDto toUserDto(User user){
        return new UserDto(user.getId(),
                user.getName(),
                user.getPassword(),
                user.getCompany(),
                new ArrayList<>());
    }

}
