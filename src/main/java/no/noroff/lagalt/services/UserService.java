package no.noroff.lagalt.services;

import no.noroff.lagalt.models.Project;
import no.noroff.lagalt.models.User;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;

public interface UserService extends CrudService<User, Integer> {

    User addByUid(Jwt jwt);

    User findByUid(String uid);

    void deleteByUid(String uid);

    Collection<Project> findRecommendations(User user);

    void addSkillset(String[] skillsetPostDTO, Integer id);

    void addToClickHistory(Integer[] projectId,Integer userId);

    void changeDescription(String[] userPostDto, Integer integer);

    void changeHiddenStatus(String uid);

    void addMember(String uId, int id);

}
