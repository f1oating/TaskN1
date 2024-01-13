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
        return !validationResult.isValid()
                ? toCompanyDto(companyRepository.save(company))
                : new CompanyDto(null, null, validationResult.getErrors());
    }

    public Company findCompanyById(Long id) throws NoCompanyFoundException {
        return this.companyRepository.findById(id)
                .orElseThrow(NoCompanyFoundException::new);
    }

    public Iterable<Company> findAllCompanies(){
        return companyRepository.findAll();
    }

    public void deleteCompanyById(Long id) throws NoCompanyFoundException {
        this.companyRepository.findById(id)
                .orElseThrow(NoCompanyFoundException::new);
        this.companyRepository.deleteById(id);
    }

    public void deleteCompany(Company company) throws NoCompanyFoundException {
        this.companyRepository.findById(company.getCompany_id())
                .orElseThrow(NoCompanyFoundException::new);
        companyRepository.delete(company);
    }

    private CompanyDto toCompanyDto(Company company){
        return new CompanyDto(company.getCompany_id(),
                company.getName(),
                new ArrayList<>());
    }

}
