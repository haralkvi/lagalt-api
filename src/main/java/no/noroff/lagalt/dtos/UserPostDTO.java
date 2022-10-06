package no.noroff.lagalt.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPostDTO {
    private String name;
    private String email;
    private String password;
    private boolean admin;
    private boolean hidden;
}
