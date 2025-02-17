package org.example.Service;

import lombok.AllArgsConstructor;
import org.example.Repository.StudentRepository;
import org.example.Repository.UnemployedRepository;
import org.example.model.Reassignment;
import org.example.model.Student;
import org.example.model.Unemployed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class UnemployedService {
    @Autowired
    private final UnemployedRepository unemployedRepository;
    @Autowired
    private final StudentRepository studentRepository;

    public Unemployed addUnemployed(Long studentId, Unemployed unemployed){
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        unemployed.setStudent(student);
        return unemployedRepository.save(unemployed);
    }

    public Unemployed getUnemployedById(Long id){
        return unemployedRepository.findByStudentId(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public List<Unemployed> getAllUnemployed(){return unemployedRepository.findAll();}

    public Slice<Unemployed> getUnemployedWithPagination(int page, int size){
        Pageable pageable = PageRequest.of(page, size) ;
        return unemployedRepository.findBy(pageable);
    }

    public Unemployed updateUnemployed(Long id, Map<String, Object> updates){
        Unemployed unemployed = unemployedRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No unemployed with such id"));

        updates.forEach((key, value) -> {
            switch (key) {
                case "profiling" -> unemployed.setProfiling((String) value);
                case "nameCompany" -> unemployed.setNameCompany((String) value);
                case "directionReturned" -> unemployed.setDirectionReturned((String) value);
                case "admissionToHigherLevel" -> unemployed.setAdmissionToHigherLevel((String) value);
                case "companyNotified" -> unemployed.setCompanyNotified((String) value);
                case "dataNotification" -> unemployed.setDataNotification((String) value);
                case "conscriptionEmployment" -> unemployed.setConscriptionEmployment((String) value);
                case "servesArmy" -> unemployed.setServesArmy((String) value);
                case "paymentOfTuitionFees" -> unemployed.setPaymentOfTuitionFees((String) value);
                case "notes" -> unemployed.setNotes((String) value);
                case "consolidation" -> unemployed.setConsolidation((String) value);
            }
        });

        return unemployedRepository.save(unemployed);
    }

    public void deleteUnemployed(Long id){
        if(!unemployedRepository.existsById(id)){throw new RuntimeException("No unemployed with such id");}
        unemployedRepository.deleteById(id);}


}
