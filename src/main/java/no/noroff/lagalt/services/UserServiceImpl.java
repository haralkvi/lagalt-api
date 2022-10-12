package no.noroff.lagalt.services;

import no.noroff.lagalt.models.Project;
import no.noroff.lagalt.models.User;
import no.noroff.lagalt.repositories.UserRepository;
import no.noroff.lagalt.util.RecommendationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

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
    public void deleteById(Integer integer) {
        userRepository.deleteById(integer);
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
        user.setName(jwt.getClaimAsString("username"));
        user.setEmail(jwt.getClaimAsString("email"));
        user.setAdmin(jwt.getClaimAsBoolean("admin"));
        user.setHidden(jwt.getClaimAsBoolean("hidden"));
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
