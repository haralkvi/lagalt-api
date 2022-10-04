package no.noroff.lagalt.repositories;

import no.noroff.lagalt.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
}
