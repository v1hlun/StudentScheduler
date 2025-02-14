package org.example.Service;

import lombok.AllArgsConstructor;
import org.example.Repository.DistributionRepository;
import org.example.Repository.StudentRepository;
import org.example.model.Distribution;
import org.example.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DistributionService {
    @Autowired
    private DistributionRepository distributionRepository;

    @Autowired
    private StudentRepository studentRepository;

    public Distribution addDistribution(Long studentId, Distribution distribution) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        distribution.setStudent(student);
        return distributionRepository.save(distribution);
    }

    public Distribution getDistributionById(Long studentId){
        return distributionRepository.findByStudentId(studentId).
                orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public Distribution updateDistribution(Long studentId,Distribution updateDistribution){
        Distribution distribution = distributionRepository.findByStudentId(studentId).
                orElseThrow(() -> new RuntimeException("Distribution data not found"));

        distribution.setProfiling(updateDistribution.getProfiling());

        return distribution;

    }
}
