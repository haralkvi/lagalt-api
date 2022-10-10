package no.noroff.lagalt.services;

import no.noroff.lagalt.models.User;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;

public interface UserService extends CrudService<User, Integer> {
    User addByUid(Jwt jwt);

    User findByUid(String uid);
}
