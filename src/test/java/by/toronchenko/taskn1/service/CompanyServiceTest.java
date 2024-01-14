package by.toronchenko.taskn1.service;

import by.toronchenko.taskn1.dto.CompanyDto;
import by.toronchenko.taskn1.entity.Company;
import by.toronchenko.taskn1.entity.User;
import by.toronchenko.taskn1.repositories.CompanyRepository;
import by.toronchenko.taskn1.util.exception.NoCompanyFoundException;
import by.toronchenko.taskn1.validators.CompanyValidator;
import by.toronchenko.taskn1.validators.ValidationResult;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private CompanyValidator companyValidator;
    @InjectMocks
    private CompanyService companyService;

    @Test
    void testSaveCompany(){
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
    void testFindCompanyById() throws NoCompanyFoundException {
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
    void testDeleteCompanyById() throws NoCompanyFoundException {

        assertThrows(NoCompanyFoundException.class, () -> companyService.deleteCompanyById(1L));
    }

    @Test
    void testDeleteCompany() throws NoCompanyFoundException {
        Company company = Company.builder()
                .name("Test")
                .build();
        User user = User.builder()
                .name("Testoviy")
                .password("test")
                .build();
        company.addUser(user);

        assertThrows(NoCompanyFoundException.class, () -> companyService.deleteCompany(company));
    }
}
