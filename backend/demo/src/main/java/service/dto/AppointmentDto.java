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

    public int getIdAppointment() {
        return idAppointment;
    }

    public void setIdAppointment(int idAppointment) {
        this.idAppointment = idAppointment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsernameUser() {
        return usernameUser;
    }

    public void setUsernameUser(String usernameUser) {
        this.usernameUser = usernameUser;
    }

    public String getUsernameTechnician() {
        return usernameTechnician;
    }

    public void setUsernameTechnician(String usernameTechnician) {
        this.usernameTechnician = usernameTechnician;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

}