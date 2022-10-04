package no.noroff.lagalt.repositories;

import no.noroff.lagalt.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
