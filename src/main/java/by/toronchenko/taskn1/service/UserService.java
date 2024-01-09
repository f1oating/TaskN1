package by.toronchenko.taskn1.service;

import by.toronchenko.taskn1.entity.Company;
import by.toronchenko.taskn1.entity.User;
import by.toronchenko.taskn1.repositories.UserCrudRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserCrudRepository userCrudRepository;

    public User saveUser(User user){
        return userCrudRepository.save(user);
    }

    public Optional<User> findUserById(Long id){
        return userCrudRepository.findById(id);
    }

    public Iterable<User> findAllUsers(){
        return userCrudRepository.findAll();
    }

    public void deleteUserById(Long id){
        userCrudRepository.deleteById(id);
    }

    public void deleteUser(User user){
        userCrudRepository.delete(user);
    }

}
