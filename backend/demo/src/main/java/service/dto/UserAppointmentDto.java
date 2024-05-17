package service.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserAppointmentDto {
    private String nameType;
    private String technicianFirstName;
    private String technicianLastName;
    private String technicianPhone;
    private String technicianEmail;
    private Date date;
    private String status;
    private int idAppointment;
}