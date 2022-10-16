package no.noroff.lagalt.services;

import no.noroff.lagalt.models.Project;
import no.noroff.lagalt.models.User;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;

public interface UserService extends CrudService<User, String> {

    User addById(Jwt jwt);

    Collection<Project> findRecommendations(User user);

    void updateSkillset(String[] skills, String id);

    void addToClickHistory(int projectId, String userId);

    void changeDescription(String description, String integer);

    void changeHiddenStatus(String uid);

    void addMember(String uId, int id);

    void addMembers(String[] members, int id);
}
