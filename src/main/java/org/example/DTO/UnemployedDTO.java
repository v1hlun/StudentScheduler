package org.example.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnemployedDTO {
    private Long id;
    private Long studentId;
    private String fullName;
    private String address;

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
