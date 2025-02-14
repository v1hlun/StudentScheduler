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
public class Unemployed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;

    private String profiling;
    private String nameCompany;
    private String directionReturned;
    private String admissionToHigherLevel;
    private String companyNotified;
    private String dataNotification;
    private String conscriptionEmployment;
    private String servesArmy;
    private String paymentOfTuitionFees;
    private String notes;
    private String consolidation;

}
