package service.dto;

import lombok.Data;

@Data
public class DashboardDto {
    private Double avgGrade;
    private String firstName;
    private String lastName;
    private Integer month;
    private Long nrAppointments;

    public DashboardDto(Double avgGrade, String firstName, String lastName) {
        this.avgGrade = avgGrade;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public DashboardDto(Integer month, Long nrAppointments){
        this.month = month;
        this.nrAppointments = nrAppointments;
    }

    public DashboardDto(String firstName, String lastName, Long NrAppointments){
        this.firstName = firstName;
        this.lastName = lastName;
        this.nrAppointments = NrAppointments;
    }

}
