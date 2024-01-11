package by.toronchenko.taskn1.validators;

import by.toronchenko.taskn1.entity.Company;
import by.toronchenko.taskn1.entity.User;
import by.toronchenko.taskn1.repositories.CompanyCrudRepository;
import by.toronchenko.taskn1.repositories.UserCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompanyValidator implements Validator<Company>{

    @Autowired
    private CompanyCrudRepository companyCrudRepository;

    @Override
    public ValidationResult isValid(Company company) {
        var validationResult = new ValidationResult();
        if(company.getName().isEmpty()){
            validationResult.add(Error.of("invalid.name", "Name is empty!"));
        }
        if(company.getName().length() > 16){
            validationResult.add(Error.of("invalid.name", "Name to long!"));
        }
        if(isExist(company)){
            validationResult.add(Error.of("database.exists", "Company already exists!"));
        }
        return validationResult;
    }

    private boolean isExist(Company company){
        return companyCrudRepository.findAll().stream().anyMatch(c ->
                (c.getName().equals(company.getName())) && (c.getCompany_id() != company.getCompany_id())
        );
    }

}
