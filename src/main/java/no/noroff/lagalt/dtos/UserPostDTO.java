package no.noroff.lagalt.dtos;

import lombok.Data;

@Data
public class UserPostDTO {
    private String name;
    private String email;
    private boolean admin;
    private boolean hidden;
}
