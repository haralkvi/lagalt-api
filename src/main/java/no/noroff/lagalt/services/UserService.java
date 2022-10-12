package no.noroff.lagalt.services;

import no.noroff.lagalt.models.User;

public interface UserService extends CrudService<User, Integer> {

    void addSkillset(String[] skillsetPostDTO, Integer id);

    void addToClickHistory(Integer[] projectId,Integer userId);

    void changeDescription(String[] userPostDto, Integer integer);

}
