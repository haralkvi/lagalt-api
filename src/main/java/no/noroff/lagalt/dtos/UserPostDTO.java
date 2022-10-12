package no.noroff.lagalt.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UserPostDTO {
    private String name;
    private String email;
    private String password;
    private boolean admin;
    private boolean hidden;
}
