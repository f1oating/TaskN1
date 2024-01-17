package by.toronchenko.taskn1.repositories;

import by.toronchenko.taskn1.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT DISTINCT e FROM users e WHERE e.name LIKE ?1%")
    public Page<User> findDistinctFirstByName(PageRequest pageRequest, String name);

    public Optional<User> findByName(String name);

}
