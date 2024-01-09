package by.toronchenko.taskn1.repositories;

import by.toronchenko.taskn1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCrudRepository extends JpaRepository<User, Long> {
}
