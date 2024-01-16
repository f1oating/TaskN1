package by.toronchenko.taskn1.service;

import by.toronchenko.taskn1.dto.CompanyDto;
import by.toronchenko.taskn1.dto.UserDto;
import by.toronchenko.taskn1.entity.Company;
import by.toronchenko.taskn1.entity.User;
import by.toronchenko.taskn1.repositories.CompanyRepository;
import by.toronchenko.taskn1.util.exception.NoCompanyFoundException;
import by.toronchenko.taskn1.util.exception.NoUserFoundException;
import by.toronchenko.taskn1.validators.CompanyValidator;
import by.toronchenko.taskn1.validators.Error;
import by.toronchenko.taskn1.validators.ValidationResult;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private CompanyValidator companyValidator;
    @InjectMocks
    private CompanyService companyService;

    @Test
    void testSaveCompany() {
        Company company = Company.builder()
                .company_id(1L)
                .name("Test")
                .build();
        User user = User.builder()
                .name("Testoviy")
                .password("test")
                .build();
        company.addUser(user);
        CompanyDto companyDto = new CompanyDto(1L, "Test", new ArrayList<>());

        Mockito.when(companyValidator.isValid(company)).thenReturn(new ValidationResult());
        Mockito.when(companyRepository.save(company)).thenReturn(company);

        CompanyDto result = companyService.saveCompany(company);

        assertEquals(companyDto, result);
    }

    @Test
    void testSaveNotValidCompany() {
        Company company = Company.builder()
                .name("")
                .build();
        ValidationResult validationResult = new ValidationResult();
        validationResult.add(Error.of("invalid.name", "Name is empty!"));

        CompanyDto companyDto = new CompanyDto(null, null, validationResult.getErrors());

        when(companyValidator.isValid(company)).thenReturn(validationResult);

        CompanyDto result = companyService.saveCompany(company);

        assertEquals(companyDto, result);
    }

    @Test
    void testFindCompanyById() {
        Company company = Company.builder()
                .company_id(1L)
                .name("Test")
                .build();
        User user = User.builder()
                .id(1L)
                .name("Testoviy")
                .password("test")
                .build();
        company.addUser(user);

        Mockito.when(companyRepository.findById(1L)).thenReturn(Optional.of(company));

        Company result = companyService.findCompanyById(1L);

        assertEquals(company, result);
    }

    @Test
    void testFindCompanyByIdWithExcpetion() {
        when(companyRepository.findById(10L)).thenThrow(NoCompanyFoundException.class);
        assertThrows(NoCompanyFoundException.class, () -> companyService.findCompanyById(10L));
    }

    @Test
    void testFindAllCompanies(){
        Company company = Company.builder()
                .name("Test")
                .build();
        User user = User.builder()
                .name("Testoviy")
                .password("test")
                .build();
        company.addUser(user);
        Company company2 = Company.builder()
                .name("Test2")
                .build();
        User user2 = User.builder()
                .name("Testoviy2")
                .password("test2")
                .build();
        company.addUser(user);
        company2.addUser(user);

        List<Company> companies = List.of(company, company2);

        Mockito.when(companyRepository.findAll()).thenReturn(companies);

        Iterable<Company> result = companyService.findAllCompanies();

        assertEquals(companies, result);
    }

    @Test
    void testFindPageCompaniesByName() {
        Company company = Company.builder()
                .name("Test")
                .build();
        User user = User.builder()
                .name("Testoviy")
                .password("test")
                .build();
        company.addUser(user);
        List<Company> companies = new ArrayList<>();
        companies.add(company);

        when(companyRepository.findAllByName(PageRequest.of(1, 5), "Test"))
                .thenReturn(new PageImpl<>(companies));
        Page<Company> result = companyService.findPageCompaniesByName(PageRequest.of(1, 5), "Test");
        assertEquals(companies, result.stream().toList());
    }

    @Test
    void testFindPageUsersByEmptyName() {
        Company company = Company.builder()
                .name("Test")
                .build();
        User user = User.builder()
                .name("Testoviy")
                .password("test")
                .build();
        company.addUser(user);
        Company company2 = Company.builder()
                .name("Test")
                .build();
        User user2 = User.builder()
                .name("Testoviy")
                .password("test")
                .build();
        company.addUser(user);
        company2.addUser(user2);
        List<Company> companies = new ArrayList<>();
        companies.add(company);
        companies.add(company2);

        when(companyRepository.findAll(PageRequest.of(1, 5)))
                .thenReturn(new PageImpl<>(companies));
        Page<Company> result = companyService.findPageCompaniesByName(PageRequest.of(1, 5), "");
        assertEquals(companies, result.stream().toList());
    }

    @Test
    void testDeleteCompanyById() {
        Company company = Company.builder()
                .company_id(1L)
                .name("Test")
                .build();

        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));
        companyService.deleteCompanyById(1L);
        verify(companyRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void testDeleteCompanyByIdWithException() {
        when(companyRepository.findById(10L)).thenThrow(NoCompanyFoundException.class);
        assertThrows(NoCompanyFoundException.class, () -> companyService.deleteCompanyById(10L));
    }

    @Test
    void testDeleteCompany() {
        Company company = Company.builder()
                .company_id(1L)
                .name("Test")
                .build();

        when(companyRepository.findById(company.getCompany_id())).thenReturn(Optional.of(company));
        companyService.deleteCompany(company);
        verify(companyRepository, Mockito.times(1)).delete(company);
    }

    @Test
    void testDeleteCompanyWithException() {
        Company company = Company.builder()
                .company_id(10L)
                .name("Test")
                .build();

        when(companyRepository.findById(company.getCompany_id())).thenThrow(NoCompanyFoundException.class);
        assertThrows(NoCompanyFoundException.class, () -> companyService.deleteCompany(company));
    }
}
