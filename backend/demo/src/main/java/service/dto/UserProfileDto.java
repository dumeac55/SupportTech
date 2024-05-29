package service.dto;

import lombok.Data;

@Data
public class UserProfileDto {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String username;
    private String role;
}
