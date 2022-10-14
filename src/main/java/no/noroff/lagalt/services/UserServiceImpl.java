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

    /** Add skills to an specified users skillset
     *
      * @param skillsetPostDTO An array of Strings, these contain skills
     * @param id Integer that refers to an specific user
     */
    public void addSkillset(String[] skillsetPostDTO, String id){
        User user = this.findById(id);
        user.setSkillSet(new HashSet<>(Arrays.asList(skillsetPostDTO)));
        userRepository.save(user);
    }

    /** Adds a project or multiple projects to a specified users click history
     *
     * @param projectId An array of integers that refers to projects' ids
     * @param id Integer that refers to an users id
     * @author Marius Olafsen
     */
    public void addToClickHistory(Integer[] projectId, String id){
        User user = this.findById(id);
        Set<Project> projects = user.getProjectsHistory();
        for(Integer s : projectId){
            Project project = projectService.findById(s);
            projects.add(project);
        }
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
    public void addMember(String uId, int id){
        Project project = projectService.findById(id);
        User user = this.findById(uId);
        Set<User> users = project.getMembers();
        users.add(user);
        project.setMembers(users);
        projectService.update(project);
    }

    /** Adds member(s) to an specified project
     *
     * @param members int that refers to users
     * @param id refers to an project
     * @author Marius Olafsen
     */
    public void addMembers(String[] members, int id){
        Project project = projectService.findById(id);
        Set<User> users = project.getMembers();
        for(String i : members){
            User user = this.findById(i);
            users.add(user);
        }
        project.setMembers(users);
        projectService.update(project);
    }
}
