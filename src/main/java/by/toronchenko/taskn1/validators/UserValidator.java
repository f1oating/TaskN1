package by.toronchenko.taskn1.validators;

import by.toronchenko.taskn1.entity.User;
import by.toronchenko.taskn1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserValidator implements Validator<User>{

    @Autowired
    private UserRepository userRepository;

    @Override
    public ValidationResult isValid(User user) {
        var validationResult = new ValidationResult();
        if(user.getName().isEmpty()){
            validationResult.add(Error.of("invalid.name", "Name is empty!"));
        }
        if(user.getName().length() > 16){
            validationResult.add(Error.of("invalid.name", "Name to long!"));
        }
        if(user.getPassword().isEmpty()){
            validationResult.add(Error.of("invalid.password", "Password is empty!"));
        }
        if(user.getPassword().length() > 16){
            validationResult.add(Error.of("invallid.password", "Password to long!"));
        }
        if(isExist(user)){
            validationResult.add(Error.of("database.exist", "User already exists!"));
        }
        return validationResult;
    }

    private boolean isExist(User user){
        return userRepository.findAll().stream().anyMatch(u ->
            (u.getName().equals(user.getName())) && (u.getId() != user.getId())
        );
    }

}
