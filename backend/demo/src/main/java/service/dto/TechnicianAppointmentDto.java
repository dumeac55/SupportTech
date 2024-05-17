package service.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TechnicianAppointmentDto {
    private String nameType;
    private String userFirstName;
    private String userLastName;
    private String userPhone;
    private String userEmail;
    private Date date;
    private String status;
    private int idAppointment;
}