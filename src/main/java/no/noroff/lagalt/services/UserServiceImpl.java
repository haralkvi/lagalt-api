package no.noroff.lagalt.services;

import no.noroff.lagalt.models.Project;
import no.noroff.lagalt.models.User;
import no.noroff.lagalt.repositories.UserRepository;
import no.noroff.lagalt.util.RecommendationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
    public User findById(Integer integer) {
        return userRepository.findById(integer).get();
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
    public void deleteById(Integer id) {
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
    public void deleteByUid(String uid) {
        userRepository.deleteByUid(uid);
    }

    @Override
    public Collection<Project> findRecommendations(User user) {
        return recommendationUtil.getRecommendedProjects(user);
    }

    @Override
    public User addByUid(Jwt jwt) {
        User user = new User();
        user.setUid(jwt.getClaimAsString("sub"));
        user.setName(jwt.getClaimAsString("name"));
        user.setEmail(jwt.getClaimAsString("email"));
        user.setHidden(false);
        userRepository.save(user);
        return user;
    }

    @Override
    public User findByUid(String uid) {
        return userRepository.findByUid(uid);
       }
       
    public void addSkillset(String[] skillsetPostDTO, Integer id){
        User user = this.findById(id);
        user.setSkillSet(new HashSet<>(Arrays.asList(skillsetPostDTO)));
        userRepository.save(user);
    }
    
    public void addToClickHistory(Integer[] projectId, Integer id){
        User user = this.findById(id);
        Set<Project> projects = user.getProjectsHistory();
        for(Integer s : projectId){
            Project project = projectService.findById(s);
            projects.add(project);
        }
        user.setProjectsHistory(projects);
        userRepository.save(user);
    }

    public void changeDescription(String[] description, Integer id){
        User user = userRepository.findById(id).get();
        user.setDescription(description[0]);
        userRepository.save(user);
      }
}
