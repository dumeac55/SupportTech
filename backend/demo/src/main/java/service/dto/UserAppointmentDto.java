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

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }

    public String getMechanicFirstName() {
        return mechanicFirstName;
    }

    public void setMechanicFirstName(String mechanicFirstName) {
        this.mechanicFirstName = mechanicFirstName;
    }

    public String getMechanicLastName() {
        return mechanicLastName;
    }

    public void setMechanicLastName(String mechanicLastName) {
        this.mechanicLastName = mechanicLastName;
    }

    public String getMechanicPhone() {
        return mechanicPhone;
    }

    public void setMechanicPhone(String mechanicPhone) {
        this.mechanicPhone = mechanicPhone;
    }

    public String getMechanicEmail() {
        return mechanicEmail;
    }

    public void setMechanicEmail(String mechanicEmail) {
        this.mechanicEmail = mechanicEmail;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
