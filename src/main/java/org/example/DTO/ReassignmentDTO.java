package org.example.DTO;


import lombok.Getter;
import lombok.Setter;
import org.example.model.Student;

@Getter
@Setter
public class ReassignmentDTO {

    private Long id;
    private Long studentId;
    private String fullName;
    private String address;
    private String telNumber;
    private String speciality;
    private String code;
    private String faculty;
    private String formEducation;
    private String paidEducation;
    private String stepEducation;
    private String yearOfGraduation;
    private String profiling;
    private String nameOldCompany;
    private String nameNewCompany;
    private String jobTitle;
    private String notes;
    private String consolidation;
}
