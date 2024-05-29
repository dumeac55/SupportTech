package service.dto;

import lombok.Data;

@Data
public class TechnicianDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String username;
    private String role;
    private Float avgGrade;
}