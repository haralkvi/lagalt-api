package no.noroff.lagalt.dtos.post;

import lombok.Data;

@Data
public class UserPostDTO {
    private String id;
    private String name;
    private String email;
    private boolean admin;
    private boolean hidden;
}
