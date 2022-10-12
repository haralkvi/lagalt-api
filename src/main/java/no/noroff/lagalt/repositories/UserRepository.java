package no.noroff.lagalt.repositories;

import no.noroff.lagalt.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUid(String uid);

    void deleteByUid(String uid);

    User findByName(String name);

}
