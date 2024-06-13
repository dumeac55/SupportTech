package service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idappointment")
    private int id;

    @ManyToOne
    @JoinColumn(name = "userprofileid", referencedColumnName = "idprofile")
    private UserProfile userProfile;

    @ManyToOne
    @JoinColumn(name = "technicianid", referencedColumnName = "idtechnician")
    private TechnicianProfile technician;

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "status")
    private String status;

    @Column(name = "typename")
    private String nameType;

    @Column(name = "price")
    private int price;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public TechnicianProfile getTechnician() {
        return technician;
    }

    public void setTechnician(TechnicianProfile technician) {
        this.technician = technician;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}