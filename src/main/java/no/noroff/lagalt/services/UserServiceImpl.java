package no.noroff.lagalt.services;

import no.noroff.lagalt.models.Project;
import no.noroff.lagalt.models.User;
import no.noroff.lagalt.repositories.UserRepository;
import no.noroff.lagalt.util.RecommendationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private RecommendationUtil recommendationUtil;

    @Autowired
    ProjectService projectService;

    @Autowired
    private ApplicationService applicationService;

    @Override
    public User findById(String id) {
        Optional<User> opt = userRepository.findById(id);
        return opt.orElse(null);
    }

    @Override
    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User add(User entity) {
        return userRepository.save(entity);
    }

    @Override
    public User update(User entity) {
        return userRepository.save(entity);
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        if (userRepository.existsById(id)) {
            User user = this.findById(id);

            // a user's projects and comments are kept even if the user is deleted
            user.getProjectsOwned().forEach(project -> project.setOwner(null));
            user.getProjectsHistory().forEach(project -> project.removeUserFromHistory(user));
            user.getProjectsParticipated().forEach(project -> project.removeUserFromMembers(user));
            user.getComments().forEach(comment -> comment.setUser(null));

            // a user's applications are deleted if the user is deleted
            user.getApplications().forEach(application -> applicationService.delete(application));

            userRepository.deleteById(id);
        }
    }

    @Override
    public void delete(User entity) {
        userRepository.delete(entity);
    }

    @Override
    public Collection<Project> findRecommendations(User user) {
        return recommendationUtil.getRecommendedProjects(user);
    }

    public User addById(Jwt jwt) {
        User user = new User();
        user.setId(jwt.getClaimAsString("sub"));
        user.setName(jwt.getClaimAsString("name"));
        user.setEmail(jwt.getClaimAsString("email"));
        user.setHidden(false);
        userRepository.save(user);
        return user;
    }

    /** Updates a given user's skill set
     *
     * @param skills An array of Strings, these contain skills
     * @param id String that refers to an specific user
     */
    public void updateSkillset(String[] skills, String id){
        User user = this.findById(id);
        user.setSkillSet(new HashSet<>(Arrays.asList(skills)));
        userRepository.save(user);
    }

    /** Adds a project or multiple projects to a specified users click history
     *
     * @param projectId An array of integers that refers to projects' ids
     * @param id Integer that refers to an users id
     * @author Marius Olafsen
     */
    public void addToClickHistory(int projectId, String id){
        // find user and user's set of clicked projects
        User user = this.findById(id);
        Set<Project> projects = user.getProjectsHistory();

        // find project to be added to user's click history
        Project project = projectService.findById(projectId);

        // add project to user's click history
        projects.add(project);

        // persist changes
        user.setProjectsHistory(projects);
        userRepository.save(user);
    }

    /** Changes the description of the current user
     *
     * @param description An array of strings where only 0 is used
     * @param id Refers to the id of an user
     * @author Marius Olafsen
     */
    public void changeDescription(String[] description, String id){
        User user = this.findById(id);
        user.setDescription(description[0]);
        userRepository.save(user);
    }

    /** Changes from hidden to "not hidden" if the user
     * is already hidden, and from "not hidden" to hidden if
     * otherwise.
     *
     * @param uid refers to currently logged in user
     * @author Marius Olafsen
     */
    public void changeHiddenStatus(String uid){
        User user = this.findById(uid);
        user.setHidden(!user.isHidden());
        userRepository.save(user);
    }

    /** Adds the current logged in user as an
     * member to a specified project
     *
     * @param uId currently logged in user, comes from JWT
     * @param id refers to an project
     * @author Marius Olafsen
     */
    @Transactional
    public void addMember(String uId, int id){
        Project project = projectService.findById(id);
        User user = this.findById(uId);
        user.getProjectsParticipated().add(project);
        userRepository.save(user);
    }

    /** Adds member(s) to a specified project
     *
     * @param members arr of String that refers to users
     * @param id refers to a project
     * @author Marius Olafsen
     */
    public void addMembers(String[] members, int id) {
        for (String member : members){
            this.addMember(member, id);
        }
    }

    @Override
    public boolean existsById(String id) {
        return userRepository.existsById(id);
    }
}
