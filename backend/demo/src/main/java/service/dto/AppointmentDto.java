package service.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AppointmentDto {
    private String usernameUser;
    private String usernameTechnician;
    private String type;
    private Date data;
    private String status;
    private int idAppointment;

}