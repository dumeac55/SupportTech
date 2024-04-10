package service.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserAppointmentDto {
    private String nameType;
    private String mehcanicFirstName;
    private String mechanicLastName;
    private String mechanicPhone;
    private String mechanicEmail;
    private Date date;

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }

    public String getMehcanicFirstName() {
        return mehcanicFirstName;
    }

    public void setMehcanicFirstName(String mehcanicFirstName) {
        this.mehcanicFirstName = mehcanicFirstName;
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
