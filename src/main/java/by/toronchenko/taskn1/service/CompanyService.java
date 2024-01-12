package by.toronchenko.taskn1.service;

import by.toronchenko.taskn1.dto.CompanyDto;
import by.toronchenko.taskn1.dto.UserDto;
import by.toronchenko.taskn1.entity.Company;
import by.toronchenko.taskn1.entity.User;
import by.toronchenko.taskn1.repositories.CompanyRepository;
import by.toronchenko.taskn1.util.exception.NoCompanyFoundException;
import by.toronchenko.taskn1.util.exception.NoUserFoundException;
import by.toronchenko.taskn1.validators.CompanyValidator;
import by.toronchenko.taskn1.validators.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CompanyValidator companyValidator;

    public CompanyDto saveCompany(Company company){
        ValidationResult validationResult = companyValidator.isValid(company);
        if(validationResult.isValid()){
            return new CompanyDto(company.getCompany_id(), company.getName(), validationResult.getErrors());
        }
        return toCompanyDto(companyRepository.save(company));
    }

    public Company findCompanyById(Long id) throws NoCompanyFoundException {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if(companyOptional.isPresent()){
            return companyOptional.get();
        }else{
            throw new NoCompanyFoundException();
        }
    }

    public Iterable<Company> findAllCompanies(){
        return companyRepository.findAll();
    }

    public void deleteCompanyById(Long id) throws NoCompanyFoundException {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if(companyOptional.isPresent()){
            companyRepository.deleteById(id);
        }else{
            throw new NoCompanyFoundException();
        }
    }

    public void deleteCompany(Company company) throws NoCompanyFoundException {
        Optional<Company> companyOptional = companyRepository.findById(company.getCompany_id());
        if(companyOptional.isPresent()){
            companyRepository.delete(company);
        }else{
            throw new NoCompanyFoundException();
        }
    }

    private CompanyDto toCompanyDto(Company company){
        return new CompanyDto(company.getCompany_id(),
                company.getName(),
                new ArrayList<>());
    }

}
