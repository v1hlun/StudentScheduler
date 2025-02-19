package org.example.Service;

import lombok.AllArgsConstructor;
import org.example.Repository.DistributionRepository;
import org.example.Repository.StudentRepository;
import org.example.model.Distribution;
import org.example.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class DistributionService {
    @Autowired
    private DistributionRepository distributionRepository;
    private WebSocketHandler webSocketHandler;

    @Autowired
    private StudentRepository studentRepository;

    public Distribution addDistribution(Long studentId, Distribution distribution) throws IOException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));


        distribution.setStudent(student);

        Distribution saved = distributionRepository.save(distribution);
        webSocketHandler.sendUpdate("distribution", saved);
        return saved;
    }

    public Distribution getDistributionById(Long id){
        return distributionRepository.findByStudentId(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public List<Distribution> getAllDistribution(){return distributionRepository.findAll();}

    public Slice<Distribution> getDistributionsWithPagination(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return distributionRepository.findBy(pageable);
    }


    public Distribution updateDistribution(Long id, Map<String, Object> updates) throws IOException {
        Distribution distribution = distributionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No distribution with such id"));

        updates.forEach((key, value) -> {
            switch (key) {
                case "profiling" -> distribution.setProfiling((String) value);
                case "nameCompany" -> distribution.setNameCompany((String) value);
                case "nameCompanyObl" -> distribution.setNameCompanyObl((String) value);
                case "nameCompanyGorod" -> distribution.setNameCompanyGorod((String) value);
                case "nameCompanyRajon" -> distribution.setNameCompanyRajon((String) value);
                case "basedNameCompany" -> distribution.setBasedNameCompany((String) value);
                case "otherOrganization" -> distribution.setOtherOrganization((String) value);
                case "writedRequestofDistribution" -> distribution.setWritedRequestofDistribution((String) value);
                case "jobTitle" -> distribution.setJobTitle((String) value);
                case "working" -> distribution.setWorking((String) value);
                case "servesArmy" -> distribution.setServesArmy((String) value);
                case "onMaternityLeave" -> distribution.setOnMaternityLeave((String) value);
                case "worked" -> distribution.setWorked((String) value);
                case "dateLetter" -> distribution.setDateLetter((String) value);
                case "reDistributed" -> distribution.setReDistributed((String) value);
                case "notes" -> distribution.setNotes((String) value);
                case "periodCompulsoryService" -> distribution.setPeriodCompulsoryService((String) value);
                case "selfCare" -> distribution.setSelfCare((String) value);
                case "consolidation" -> distribution.setConsolidation((String) value);
            }
        });

        Distribution updated = distributionRepository.save(distribution);

        webSocketHandler.sendUpdate("distribution", updated);
        return updated;
        }



    public void deleteDistribution(Long id) throws IOException {
        if(!distributionRepository.existsById(id)){throw new RuntimeException("No distribution with such id");}
        distributionRepository.deleteById(id);

        webSocketHandler.sendUpdate("distribution", "Distribution deleted: " + id);
    }

}
