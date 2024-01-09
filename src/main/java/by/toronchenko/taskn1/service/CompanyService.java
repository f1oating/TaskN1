package by.toronchenko.taskn1.service;

import by.toronchenko.taskn1.entity.Company;
import by.toronchenko.taskn1.repositories.CompanyCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyCrudRepository companyCrudRepository;
    private final static CompanyService INSTANCE = new CompanyService();

    public Company saveCompany(Company company){
        return companyCrudRepository.save(company);
    }

    public Optional<Company> findCompanyById(Long id){
        return companyCrudRepository.findById(id);
    }

    public Iterable<Company> findAllUsers(){
        return companyCrudRepository.findAll();
    }

    public void deleteCompanyById(Long id){
        companyCrudRepository.deleteById(id);
    }

    public void deleteCompany(Company company){
        companyCrudRepository.delete(company);
    }

    public static CompanyService getInstance(){
        return INSTANCE;
    }
}
