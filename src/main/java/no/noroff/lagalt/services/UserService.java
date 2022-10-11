package no.noroff.lagalt.services;

import no.noroff.lagalt.dtos.DescriptionPostDTO;
import no.noroff.lagalt.dtos.SkillSetPostDTO;
import no.noroff.lagalt.dtos.UserPostDTO;
import no.noroff.lagalt.models.User;

public interface UserService extends CrudService<User, Integer> {

    void addSkillset(SkillSetPostDTO skillsetPostDTO);

    void addToClickHistory(UserPostDTO userPostDTO,Integer projectId);

    void changeDescription(DescriptionPostDTO userPostDto, Integer integer);

}
