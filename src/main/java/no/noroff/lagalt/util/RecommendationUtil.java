package no.noroff.lagalt.util;

import no.noroff.lagalt.models.Project;
import no.noroff.lagalt.models.User;
import no.noroff.lagalt.services.ProjectService;
import no.noroff.lagalt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class RecommendationUtil {

    @Autowired
    ProjectService projectService;


    public Collection<Project> calculateRecommendedProjects(User user) {
//        Collection<Project> clickedProjects = findProjectsClickedByUser()
        return null;
    }
}
