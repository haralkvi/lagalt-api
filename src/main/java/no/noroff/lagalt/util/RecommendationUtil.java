package no.noroff.lagalt.util;

import no.noroff.lagalt.models.Project;
import no.noroff.lagalt.models.User;
import no.noroff.lagalt.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RecommendationUtil {

    @Autowired
    ProjectService projectService;

    private static int NUMBER_OF_RECOMMENDATIONS = 10;


    public Collection<Project> getRecommendedProjects(User user) {
        Collection<Project> allProjects = projectService.findAll();
        Collection<String> userSkills = user.getSkillSet();

        Collection<Project> recommendations = calculateRecommendedProjects(allProjects, userSkills);

        return recommendations;
    }

    private Collection<Project> calculateRecommendedProjects(Collection<Project> projects,
                                                             Collection<String> skills)
    {
        Map<Project, Double> projectScores = new HashMap<>();

        for (Project project : projects) {
            double projectScore = calculateProjectScore(skills, project);
            projectScores.put(project, projectScore);
        }

        Collection<Project> recommendations = extractTopProjects(projectScores);

        return recommendations;
    }

    private Collection<Project> extractTopProjects(Map<Project, Double> projectScores) {
        Collection<Project> topProjects = new HashSet<>();

        // find the top n projects
        int n = NUMBER_OF_RECOMMENDATIONS;
        while (n > 0) {
            Map.Entry<Project, Double> maxEntry = Collections.max(
                    projectScores.entrySet(),
                    Comparator.comparing(Map.Entry::getValue));
            topProjects.add(maxEntry.getKey());
            projectScores.remove(maxEntry);
            n--;
        }

        return topProjects;
    }

    private double calculateProjectScore(Collection<String> skills, Project project) {
        double matches = 0;

        for (String tag : project.getTags()) {
            if (skills.contains(tag)) {
                matches++;
            }
        }

        return skills.size() / matches;
    }
}
