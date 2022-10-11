package no.noroff.lagalt.services;

import no.noroff.lagalt.dtos.SkillSetPostDTO;
import no.noroff.lagalt.models.User;
import no.noroff.lagalt.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;


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

    public void addSkillset(SkillSetPostDTO skillsetPostDTO){
        User user = userRepository.findById(skillsetPostDTO.getId()).get();
        String skills = skillsetPostDTO.getSkill();
        String[] skillsSplit = skills.split(",");
        user.setSkillSet(new HashSet<>(Arrays.asList(skillsSplit)));
        userRepository.save(user);
    }
}
