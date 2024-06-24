package service.dto;

import lombok.Data;

@Data
public class DashboardDto {
    private Double avgGrade;
    private String firstName;
    private String lastName;
    private Integer month;
    private Long nrAppointments;
    private Long totalMoney;
    private String status;
    private Long totalAppointments;

    //Avg for Technicians
    public DashboardDto(Double avgGrade, String firstName, String lastName) {
        this.avgGrade = avgGrade;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // nr Appointments per year
    public DashboardDto(Integer month, Long nrAppointments){
        this.month = month;
        this.nrAppointments = nrAppointments;
    }
    // Nr appointments per user/technician
    public DashboardDto(String firstName, String lastName, Long NrAppointments){
        this.firstName = firstName;
        this.lastName = lastName;
        this.nrAppointments = NrAppointments;
    }

    public DashboardDto(Integer month, Long totalMoney, Long nr) {
        this.month = month;
        this.totalMoney = totalMoney;
    }

    public DashboardDto(String status, Long totalAppointments) {
        this.totalAppointments = totalAppointments;
        this.status = status;
    }
}
