package no.noroff.lagalt.util;

import no.noroff.lagalt.models.Project;
import no.noroff.lagalt.models.User;
import no.noroff.lagalt.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A helper class for finding recommended projects for a given user.
 * It is currently simultaneously extremely rudimentary and a hot mess.
 * Please don't hesitate to clean up, improve and extend this class.
 */
@Component
public class RecommendationUtil {

    @Autowired
    ProjectRepository projectRepository;

    private static final int NUMBER_OF_RECOMMENDATIONS = 10;


    /**
     * Methods sets up calculation of recommended projects by fetching all
     * projects in database, and users' skills.
     *
     * @param user for whom recommendations are fetched
     * @return a collection of recommended projects
     */
    public List<Project> getRecommendedProjects(User user) {
        Collection<Project> allProjects = projectRepository.findAll();
        Collection<String> userSkills = user.getSkillSet();

        List<Project> recommendations = calculateRecommendedProjects(allProjects, userSkills);

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
    private List<Project> calculateRecommendedProjects(Collection<Project> projects,
                                                             Collection<String> skills)
    {
        Map<Project, Double> projectScores = new HashMap<>();

        for (Project project : projects) {
            double projectScore = calculateProjectScore(skills, project);
            projectScores.put(project, projectScore);
        }

        List<Project> recommendations = extractTopProjects(projectScores);

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

        for (String neededSkill : project.getSkillsNeeded()) {
            System.out.println("neededSkill = " + neededSkill);
            if (skills.contains(neededSkill)) {
                matches++;
            }
        }

        return matches / skills.size();
    }

    /**
     * Returns an unordered collection of the n projects with the highest
     * recommendations scores as provided by @param projectScores map.
     *
     * @param projectScores mapping from Project to its recommendation score
     * @return an unordered collection of n projects
     */
    private List<Project> extractTopProjects(Map<Project, Double> projectScores) {
        List<Project> topProjects = new ArrayList<>();

        // find the top n projects
        int n = NUMBER_OF_RECOMMENDATIONS;
        while (n > 0 && !projectScores.isEmpty()) {
            Map.Entry<Project, Double> maxEntry = Collections.max(
                    projectScores.entrySet(),
                    Comparator.comparing(Map.Entry::getValue));
            topProjects.add(maxEntry.getKey());
            projectScores.remove(maxEntry.getKey());
            n--;
        }

        return topProjects;
    }
}
