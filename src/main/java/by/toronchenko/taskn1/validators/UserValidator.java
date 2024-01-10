package by.toronchenko.taskn1.validators;

import by.toronchenko.taskn1.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserValidator implements Validator<User>{

    @Override
    public ValidationResult isValid(User user) {
        var validationResult = new ValidationResult();
        if(user.getName().isEmpty()){
            validationResult.add(Error.of("invalid.name", "Name is empty!"));
        }
        if(user.getPassword().isEmpty()){
            validationResult.add(Error.of("invalid.password", "Password is empty!"));
        }
        return validationResult;
    }

}
