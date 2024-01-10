package by.toronchenko.taskn1.validators;

import by.toronchenko.taskn1.entity.Company;
import by.toronchenko.taskn1.entity.User;
import org.springframework.stereotype.Component;

@Component
public class CompanyValidator implements Validator<Company>{

    @Override
    public ValidationResult isValid(Company company) {
        var validationResult = new ValidationResult();
        if(company.getName().isEmpty()){
            validationResult.add(Error.of("invalid.name", "Name is empty!"));
        }
        return validationResult;
    }

}
