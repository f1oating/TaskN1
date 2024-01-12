package by.toronchenko.taskn1.service;

import by.toronchenko.taskn1.dto.UserDto;
import by.toronchenko.taskn1.entity.User;
import by.toronchenko.taskn1.repositories.UserRepository;
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
        if(validationResult.isValid()){
            return new UserDto(null, null, null, null, validationResult.getErrors());
        }
        return toUserDto(userRepository.save(user));
    }

    public User findUserById(Long id) throws NoUserFoundException {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            return userOptional.get();
        }else{
            throw new NoUserFoundException();
        }
    }

    public Iterable<User> findAllUsers(){
        return userRepository.findAll();
    }

    public void deleteUserById(Long id) throws NoUserFoundException {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            userRepository.deleteById(id);
        }else{
            throw new NoUserFoundException();
        }
    }

    public void deleteUser(User user) throws NoUserFoundException {
        Optional<User> userOptional = userRepository.findById(user.getId());
        if(userOptional.isPresent()){
            userRepository.delete(user);
        }else{
            throw new NoUserFoundException();
        }
    }

    private UserDto toUserDto(User user){
        return new UserDto(user.getId(),
                user.getName(),
                user.getPassword(),
                user.getCompany(),
                new ArrayList<>());
    }

}
