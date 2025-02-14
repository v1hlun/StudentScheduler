package org.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Distribution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;

    private String profiling;
    private String nameCompany;
    private String nameCompanyObl;
    private String nameCompanyGorod;
    private String nameCompanyRajon;
    private String basedNameCompany;
    private String otherOrganization;
    private String writedRequestofDistribution;
    private String writedRequestofDistributionIP;
    private String targetAgreement;
    private String olympiad;
    private String confirmationArrivalEnterprise;
    private String jobTitle;
    private String working;
    private String servesArmy;
    private String onMaternityLeave;
    private String worked;
    private String dateLetter;
    private String reDistributed;
    private String notes;
    private String periodCompulsoryService;
    private String selfCare;
    private String consolidation;

    // Геттеры и сеттеры
}
