package service.dto;

import lombok.Data;

@Data
public class SignUpDto {
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String LastName;
    private String phone;

}