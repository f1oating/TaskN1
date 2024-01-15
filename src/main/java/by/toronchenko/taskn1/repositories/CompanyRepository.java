package by.toronchenko.taskn1.repositories;

import by.toronchenko.taskn1.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    public Page<Company> findAllByName(PageRequest pageRequest, String name);

}
