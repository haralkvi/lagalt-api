package no.noroff.lagalt.repositories;

import no.noroff.lagalt.models.Application;
import no.noroff.lagalt.models.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

}
