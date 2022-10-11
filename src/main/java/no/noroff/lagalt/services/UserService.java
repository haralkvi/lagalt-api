package no.noroff.lagalt.services;

import no.noroff.lagalt.dtos.SkillSetPostDTO;
import no.noroff.lagalt.models.User;

public interface UserService extends CrudService<User, Integer> {

    void addSkillset(SkillSetPostDTO skillsetPostDTO);

}
