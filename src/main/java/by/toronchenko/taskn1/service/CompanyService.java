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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    public Company findCompanyById(Long id) {
        return this.companyRepository.findById(id)
                .orElseThrow(NoCompanyFoundException::new);
    }

    public List<Company> findAllCompanies(){
        return companyRepository.findAll();
    }

    public Page<Company> findPageCompaniesByName(PageRequest pageRequest, String name) {
        return name != null ? companyRepository.findAllByName(pageRequest, name) : companyRepository.findAll(pageRequest);
    }

    public void deleteCompanyById(Long id) {
        this.companyRepository.findById(id)
                .orElseThrow(NoCompanyFoundException::new);
        this.companyRepository.deleteById(id);
    }

    public void deleteCompany(Company company) {
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
