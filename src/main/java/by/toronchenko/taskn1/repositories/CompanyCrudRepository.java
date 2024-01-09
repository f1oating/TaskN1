package by.toronchenko.taskn1.repositories;

import by.toronchenko.taskn1.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyCrudRepository extends JpaRepository<Company, Long> {
}
