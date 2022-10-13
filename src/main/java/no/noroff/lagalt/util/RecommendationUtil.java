package no.noroff.lagalt.util;

import no.noroff.lagalt.models.Project;
import no.noroff.lagalt.models.User;
import no.noroff.lagalt.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * A helper class for finding recommended projects for a given user.
 * It is currently simultaneously extremely rudimentary and a hot mess.
 * Please don't hesitate to clean up, improve and extend this class.
 */
@Component
public class RecommendationUtil {

    @Autowired
    ProjectRepository projectRepository;

    private static int NUMBER_OF_RECOMMENDATIONS = 10;


    /**
     * Methods sets up calculation of recommended projects by fetching all
     * projects in database, and users' skills.
     *
     * @param user for whom recommendations are fetched
     * @return a collection of recommended projects
     */
    public Collection<Project> getRecommendedProjects(User user) {
        Collection<Project> allProjects = projectRepository.findAll();
        Collection<String> userSkills = user.getSkillSet();

        Collection<Project> recommendations = calculateRecommendedProjects(allProjects, userSkills);

        return recommendations;
    }

    /**
     * Calculates and returns the highest recommended projects for a given
     * user based on that user's set of skills.
     *
     * @param projects a collection of projects
     * @param skills a collection of a user's skills
     * @return collection of projects
     */
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

    /**
     * Returns a recommendation score for the given project. Each project's
     * score is calculated as a percentage of a user's skills being contained
     * in the project's collection of tags.
     *
     * @param skills collection of Strings each describing a user's skill
     * @param project a project whose recommendation score is being calculated
     * @return project's recommendation score
     */
    private double calculateProjectScore(Collection<String> skills, Project project) {
        double matches = 0;

        for (String tag : project.getTags()) {
            if (skills.contains(tag)) {
                matches++;
            }
        }

        return skills.size() / matches;
    }

    /**
     * Returns an unordered collection of the n projects with the highest
     * recommendations scores as provided by @param projectScores map.
     *
     * @param projectScores mapping from Project to its recommendation score
     * @return an unordered collection of n projects
     */
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
}
