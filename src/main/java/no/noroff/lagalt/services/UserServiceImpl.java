package no.noroff.lagalt.services;

import no.noroff.lagalt.models.User;
import no.noroff.lagalt.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Collection;

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

    @Override
    public void deleteByUid(String uid) {
        userRepository.deleteByUid(uid);
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
}
