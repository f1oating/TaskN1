package by.toronchenko.taskn1.service;

import by.toronchenko.taskn1.entity.Company;
import by.toronchenko.taskn1.entity.User;
import by.toronchenko.taskn1.repositories.CompanyCrudRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    @Mock
    private CompanyCrudRepository companyCrudRepository;
    @InjectMocks
    private CompanyService companyService;

    @Test
    void testSaveCompany(){
        Company company = Company.builder()
                .name("Test")
                .build();
        User user = User.builder()
                .name("Testoviy")
                .password("test")
                .build();
        company.addUser(user);

        Mockito.when(companyCrudRepository.save(company)).thenReturn(company);

        Company result = companyService.saveCompany(company);

        assertEquals(company, result);
    }

    @Test
    void testFindCompanyById(){
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

        Mockito.when(companyCrudRepository.findById(1L)).thenReturn(Optional.of(company));

        Optional<Company> optionalCompany = companyService.findCompanyById(1L);

        assertEquals(company, optionalCompany.get());
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

        Mockito.when(companyCrudRepository.findAll()).thenReturn(companies);

        Iterable<Company> result = companyService.findAllCompanies();

        assertEquals(companies, result);
    }

    @Test
    void testDeleteCompanyById(){
        companyService.deleteCompanyById(1L);
        Mockito.verify(companyCrudRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void testDeleteCompany(){
        Company company = Company.builder()
                .name("Test")
                .build();
        User user = User.builder()
                .name("Testoviy")
                .password("test")
                .build();
        company.addUser(user);

        companyService.deleteCompany(company);
        Mockito.verify(companyCrudRepository, Mockito.times(1)).delete(company);
    }
}
