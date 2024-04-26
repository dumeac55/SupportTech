package service.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserAppointmentDto {
    private String nameType;
    private String mechanicFirstName;
    private String mechanicLastName;
    private String mechanicPhone;
    private String mechanicEmail;
    private Date date;
    private String status;
    private int idAppointment;
}
