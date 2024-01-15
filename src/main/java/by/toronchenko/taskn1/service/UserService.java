package by.toronchenko.taskn1.service;

import by.toronchenko.taskn1.dto.UserDto;
import by.toronchenko.taskn1.entity.User;
import by.toronchenko.taskn1.repositories.UserRepository;
import by.toronchenko.taskn1.util.exception.NoCompanyFoundException;
import by.toronchenko.taskn1.util.exception.NoUserFoundException;
import by.toronchenko.taskn1.validators.UserValidator;
import by.toronchenko.taskn1.validators.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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

    public User findUserById(Long id) {
        return this.userRepository.findById(id)
                .orElseThrow(NoUserFoundException::new);
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public Page<User> findPageUsersByName(PageRequest pageRequest, String name) {
        return name != null ? userRepository.findAllByName(pageRequest, name) : userRepository.findAll(pageRequest);
    }

    public void deleteUserById(Long id) {
        this.userRepository.findById(id)
                .orElseThrow(NoUserFoundException::new);
        this.userRepository.deleteById(id);
    }

    public void deleteUser(User user) {
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
