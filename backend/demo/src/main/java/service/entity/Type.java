package service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "type")
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtype")
    private int id;

    @Column(name = "nametype")
    private String nameType;
    @Column(name = "price")
    private int price;

    @ManyToOne
    @JoinColumn(name = "technicianid", referencedColumnName = "idtechnician")
    private TechnicianProfile technicianProfile;

    public TechnicianProfile getTechnicianProfile() {
        return technicianProfile;
    }

    public void setTechnicianProfile(TechnicianProfile technicianProfile) {
        this.technicianProfile = technicianProfile;
    }

    public Type(String nameType, int price) {
        this.nameType = nameType;
        this.price = price;
    }
    public Type() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
