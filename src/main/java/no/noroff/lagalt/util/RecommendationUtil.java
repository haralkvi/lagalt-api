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

    private static int NUMBER_OF_RECOMMENDATIONS = 5;


    /**
     * Methods sets up calculation of recommended projects by fetching all
     * projects in database, and users' skills.
     *
     * @param user for whom recommendations are fetched
     * @return a collection of recommended projects
     */
    public Collection<Project> getRecommendedProjects(User user) {
        System.out.println("ENTERING getRecommendedProjects");
        Collection<Project> allProjects = projectRepository.findAll();
        System.out.println("allProjects = " + allProjects.stream().map( Project::getName).collect(Collectors.toSet()));
        Collection<String> userSkills = user.getSkillSet();
        System.out.println("userSkills: " + userSkills);

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
        System.out.println("ENTERING calculateRecommendedProjects");

        Map<Project, Double> projectScores = new HashMap<>();

        for (Project project : projects) {
            System.out.println("project = " + project.getName());
            double projectScore = calculateProjectScore(skills, project);
            projectScores.put(project, projectScore);
        }

        System.out.println("projectScores = " + projectScores);
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
        System.out.println("ENTERING calculateProjectScore");

        double matches = 0;

        for (String neededSkill : project.getSkillsNeeded()) {
            System.out.println("neededSkill = " + neededSkill);
            if (skills.contains(neededSkill)) {
                matches++;
            }
        }
        System.out.println("Found " + matches + " matches for project " + project.getName());
        System.out.println("This gives a total score of: " + (matches / skills.size()));
        return matches / skills.size();
    }

    /**
     * Returns an unordered collection of the n projects with the highest
     * recommendations scores as provided by @param projectScores map.
     *
     * @param projectScores mapping from Project to its recommendation score
     * @return an unordered collection of n projects
     */
    private Collection<Project> extractTopProjects(Map<Project, Double> projectScores) {
        System.out.println("ENTERING extractTopProjects");

        Collection<Project> topProjects = new HashSet<>();

        // find the top n projects
        System.out.println("ENTERING LOOP");
        int n = NUMBER_OF_RECOMMENDATIONS;
        while (n > 0 && !projectScores.isEmpty()) {
            System.out.println("n = " + n);
            Map.Entry<Project, Double> maxEntry = Collections.max(
                    projectScores.entrySet(),
                    Comparator.comparing(Map.Entry::getValue));
            System.out.println("maxEntry = " + maxEntry);
            topProjects.add(maxEntry.getKey());
            projectScores.remove(maxEntry.getKey());
            System.out.println("topProjects = " + topProjects);
            System.out.println("projectScores = " + projectScores);
            System.out.println("Is projectScores empty? " + projectScores.isEmpty());
            n--;
        }

        return topProjects;
    }
}
