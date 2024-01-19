package by.toronchenko.taskn1.repositories;

import by.toronchenko.taskn1.entity.GitHubUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GitHubUserRepository extends JpaRepository<GitHubUser, String> {

    Optional<GitHubUser> findByName(String name);

}
