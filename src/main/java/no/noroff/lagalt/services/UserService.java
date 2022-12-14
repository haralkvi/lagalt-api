package no.noroff.lagalt.services;

import no.noroff.lagalt.models.Project;
import no.noroff.lagalt.models.User;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;

public interface UserService extends CrudService<User, String> {

    User addById(Jwt jwt);

    List<Project> findRecommendations(User user);

    void updateSkillset(String[] skills, String id);

    boolean addToClickHistory(int projectId, String userId);

    void changeDescription(String[] description, String integer);

    void changeHiddenStatus(String uid);

    void addMember(String uId, int id);

    void addMembers(String[] members, int id);

    boolean existsByEmail(String email);

    void removeMembers(String[] members, int id);
}
