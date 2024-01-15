package by.toronchenko.taskn1.validators;

import by.toronchenko.taskn1.entity.Company;
import by.toronchenko.taskn1.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompanyValidator implements Validator<Company>{

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public ValidationResult isValid(Company company) {
        var validationResult = new ValidationResult();
        if(company.getName().trim().isEmpty()){
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
        return companyRepository.findAll().stream().anyMatch(c ->
                (c.getName().equals(company.getName())) && (c.getCompany_id() != company.getCompany_id())
        );
    }

}
